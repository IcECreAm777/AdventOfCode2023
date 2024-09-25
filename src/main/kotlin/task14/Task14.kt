package task14

import Task
import java.io.File

/** Class used to determine the results of day 14 */
class Task14(inputFileName: String, resultFileName: String) : Task(inputFileName, resultFileName) {

    private val defaultConfig = mutableListOf<MutableList<Stone>>()
    private var alteredConfig = mutableListOf<MutableList<Stone>>()
    private val numCycles = 1000000000

    override fun initializeTask() {
        File(inputFileName).forEachLine {
            val lineConfig = mutableListOf<Stone>()
            for (index in it.indices) {
                lineConfig.add(translateCharToEnum(it[index]))
            }
            defaultConfig.add(lineConfig)
        }

        alteredConfig = defaultConfig.toMutableList()
    }

    override fun generateFirstSubTaskResult(): String {

        // do a slide to the north
        slideToDirection(Pair(-1, 0))

        // return the weight as our result
        val sumWeight = calculateWeightOnNorthBeams()
        return sumWeight.toString()
    }

    override fun generateSecondSubTaskResult(): String {

        // do the specified amount of cycles where the plane gets tilted in every direction one after the other
        for(i in 0..<numCycles) {
            slideToDirection(Pair(-1, 0))   // north
            slideToDirection(Pair(0, -1))   // west
            slideToDirection(Pair(1, 0))    // south
            slideToDirection(Pair(0, 1))    // east

            print("Cycle $i / ${numCycles}\r")
        }

        // return the weight on the north beam as the result
        print("\r")
        val weight = calculateWeightOnNorthBeams()
        return weight.toString()
    }


    /** Converts the character of the input into the enumerator */
    private fun translateCharToEnum(char: Char) : Stone {
        return when (char) {
            'O' -> Stone.LOOSE
            '#' -> Stone.SOLID
            else -> Stone.EMPTY
        }
    }

    /** Tilts the plane to the specified direction so that every loose stone starts to slide in that direction
     * @param direction The direction the plane is tilted (the direction the loose stones are sliding)
     */
    private fun slideToDirection(direction: Pair<Int, Int>) {
        // iterate through every line for the sliding
        for(lineIndex in alteredConfig.indices) {
            // iterate through every column
            for (colIndex in alteredConfig[lineIndex].indices) {
                // make sure a loose stone is encountered
                val currentSpace = alteredConfig[lineIndex][colIndex]
                if(currentSpace != Stone.LOOSE) continue

                // move the stone to its new place
                val (newLine, column) = getSlidingCoordinates(Pair(lineIndex, colIndex), direction)
                alteredConfig[lineIndex][colIndex] = Stone.EMPTY
                alteredConfig[newLine][column] = Stone.LOOSE
            }
        }
    }

    /** Gets the new coordinates based on the specified starting coordinates in the specified direction
     * @param startCoordinates The coordinates a stone starts sliding at
     * @param direction The direction of the sliding (e.g. -1, 0) for north)
     * @return The new coordinates for a stone
     */
    private fun getSlidingCoordinates(startCoordinates: Pair<Int, Int>, direction: Pair<Int, Int>, ) : Pair<Int, Int> {
        // init the coordinates used
        var column = startCoordinates.second + direction.second
        var line = startCoordinates.first + direction.first

        // look for the empty space possible up north
        while(line >= 0 && column >= 0 && line < alteredConfig.size && column < alteredConfig[0].size
                && alteredConfig[line][column] == Stone.EMPTY) {
            line += direction.first
            column += direction.second
        }

        // return the previous coordinates since the current ones are blocked
        return Pair(line - direction.first, column - direction.second)
    }

    /** Calculates the weight on the north beams based on the specified configuration of stones
     * @return The weight based on the definition inside the task
     */
    private fun calculateWeightOnNorthBeams() : Int {
        var sumWeight = 0
        val numLines = alteredConfig.size
        for (lineIndex in alteredConfig.indices) {
            val numStones = alteredConfig[lineIndex].count { it == Stone.LOOSE }
            sumWeight += (numLines - lineIndex) * numStones
        }
        return sumWeight
    }
}