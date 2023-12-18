package task16

import Task
import java.io.File
import kotlin.math.abs
import kotlin.math.sign

class Task16(inputFileName: String, resultFileName: String) : Task(inputFileName, resultFileName) {

    private val lines = mutableListOf<String>()
    private val energized = mutableListOf<MutableList<Boolean>>()

    override fun initializeTask() {
        File(inputFileName).forEachLine {
            lines.add(it)
            energized.add(MutableList(it.length) { false })
        }
    }

    override fun generateFirstSubTaskResult(): String {
        // shoot a single laser from the top left corner which is the result for task 1
        val numEnergized = shootLaser(Pair(0, -1), Pair(0, 1))
        return numEnergized.toString()
    }

    override fun generateSecondSubTaskResult(): String {

        // loop variables
        var max = Int.MIN_VALUE
        val numCols = lines[0].length

        // shoot a laser from each edge on the left and right
        for(lineIndex in lines.indices) {
            // shoot from the left
            resetEnergyField()
            val leftNum = shootLaser(Pair(lineIndex, -1), Pair(0, 1))
            max = if(leftNum > max) leftNum else max

            // shoot from the right
            resetEnergyField()
            val rightNum = shootLaser(Pair(lineIndex, numCols), Pair(0, -1))
            max = if(rightNum > max) rightNum else max
        }

        // shoot a laser from each edge at the top and bottom
        for(colIndex in 0..<numCols) {
            // shoot a laser from the top
            resetEnergyField()
            val topNum = shootLaser(Pair(-1, colIndex), Pair(1, 0))
            max = if(topNum > max) topNum else max

            // shoot a laser from the bottom
            resetEnergyField()
            val bottomNum = shootLaser(Pair(lines.size, colIndex), Pair(-1, 0))
            max = if (bottomNum > max) bottomNum else max
        }

        // return the minimum as the result of this task
        return max.toString()
    }


    /** Starts shooting a laser through the mirror maze
     * @param startCoords The coordinates where the laser enters the grid (should be outside the actual grid)
     * @param startDirection The initial direction of the laser
     * @return How many fields are energized by the laser
     */
    private fun shootLaser(startCoords: Pair<Int, Int>, startDirection: Pair<Int, Int>) : Int {
        // keep track of all the laser beams
        val activeLaser = mutableListOf(Laser(startCoords, startDirection))

        // iterate until all laser beams died down
        while (activeLaser.isNotEmpty()) {

            // check every active laser beam
            val removeQueue = mutableListOf<Laser>()
            for (index in activeLaser.indices) {
                // tick the laser to check if it continues or if it dies or if it splits and energize the tile
                val currentLocation = activeLaser[index].location
                val (firstTickedLaser, secondTickedLaser) = tickLaser(activeLaser[index])
                energizeField(currentLocation)

                // replace or remove the first laser
                if(firstTickedLaser != null)
                    activeLaser[index] = firstTickedLaser
                else
                    removeQueue.add(activeLaser[index])

                // add the second laser to the list if the laser was split
                if(secondTickedLaser != null) activeLaser.add(secondTickedLaser)
            }

            // remove the queue
            for(laser in removeQueue) activeLaser.remove(laser)
        }

        // count all the energized tiles
        var numEnergized = 0
        energized.map {list -> list.map{ isEnergized -> if(isEnergized) numEnergized++ }}

        return numEnergized
    }

    /** Takes a laser and moves it along the grid one step
     * @param laser The laser to move
     * @return A pair of laser which are the next steps (two if split, one if traversed or reflected, none if end
     * was reached)
     */
    private fun tickLaser(laser: Laser) : Pair<Laser?, Laser?> {
        // get the next location
        val nextLocation = Pair(laser.location.first + laser.direction.first,
            laser.location.second + laser.direction.second)

        // laser is out of bounds
        if(nextLocation.first < 0 || nextLocation.first >= lines.size
            || nextLocation.second < 0 || nextLocation.second >= lines[0].length) return Pair(null, null)

        // get the next tile and if its energized
        val nextTile = lines[nextLocation.first][nextLocation.second]
        val nextEnergized = energized[nextLocation.first][nextLocation.second]

        // pass through empty space in every case
        if(nextTile == '.') return Pair(Laser(nextLocation, laser.direction), null)

        // encountering a splitter
        if(nextTile == '|' || nextTile == '-') {
            // treat it as empty space when laser can pass through it
            if(canPassThrough(nextTile, laser.direction)) return Pair(Laser(nextLocation, laser.direction), null)

            // return nothing when space was already energized (loop)
            if(nextEnergized) return Pair(null, null)

            // split the direction otherwise
            val (orthogonal, invOrthogonal) = splitDirection(laser.direction)
            return Pair(Laser(nextLocation, orthogonal), Laser(nextLocation, invOrthogonal))
        }

        // encountering a mirror (the last case so no if)
        val reflectedDirection = reflectLaser(nextTile, laser.direction)
        return Pair(Laser(nextLocation, reflectedDirection), null)
    }

    /** Takes the incoming direction and splits it into the two orthogonal directions
     * @param direction the direction to split
     * @return A pair of the orthogonal directions
     */
    private fun splitDirection(direction: Pair<Int, Int>) : Pair<Pair<Int, Int>, Pair<Int, Int>> {
        val first = Pair(direction.second, direction.first)
        val second = Pair(-direction.second, -direction.first)
        return Pair(first, second)
    }

    /** Reflects the laser based on the specified mirror
     * @param char The mirror to reflect at
     * @param direction The direction from where the laser hits
     * @return The new reflected direction
     */
    private fun reflectLaser(char: Char, direction: Pair<Int, Int>) : Pair<Int, Int> {
        val sign = if(char == '/') -1 else 1
        return Pair(direction.second * sign, direction.first * sign)
    }

    /** Checks if the laser can pass through the specified straight mirror
     * @param char the mirror character the laser gets split at
     * @param direction The direction of the laser when hitting the splitter
     * @return true when laser can pass through, false when laser should be split
     */
    private fun canPassThrough(char: Char, direction: Pair<Int, Int>) : Boolean {
        return (char == '-' && abs(direction.second) == 1) || (char == '|' && abs(direction.first) == 1)
    }

    /** Energizes a filed at the specified coordinates
     * @param coordinates The coordinates a field was energized
     */
    private fun energizeField(coordinates: Pair<Int, Int>) {
        // invalid coordinates
        if(coordinates.first < 0 || coordinates.first >= lines.size || coordinates.second < 0
            || coordinates.second >= lines[0].length) return

        energized[coordinates.first][coordinates.second] = true
    }

    /** Resets the energy field to start tracking for a different laser */
    private fun resetEnergyField() {
        for(lineIndex in energized.indices) {
            for(colIndex in energized[lineIndex].indices) {
                energized[lineIndex][colIndex] = false
            }
        }
    }
}