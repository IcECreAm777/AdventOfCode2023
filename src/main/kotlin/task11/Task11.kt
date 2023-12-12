package task11

import Task
import java.io.File
import kotlin.math.abs

/** Class for day 11 */
class Task11(inputFileName: String, resultFileName: String) : Task(inputFileName, resultFileName) {

    private val galaxyMap = mutableListOf<MutableList<Boolean>>()
    private var galaxyCoords = mutableListOf<Pair<Long, Long>>()
    private val emptyLines = mutableListOf<Int>()
    private val emptyCols = mutableListOf<Int>()

    override fun initializeTask() {
        // create the initial galaxy map
        var lineIndex = 0.toLong()
        File(inputFileName).forEachLine {
            // the parsed input for the line
            val line = mutableListOf<Boolean>()

            // check every character for a galaxy
            for (i in it.indices) {
                // check if a galaxy is present (a '#' is there inside the input)
                val galaxyPresent = it[i] == '#'
                line.add(galaxyPresent)

                // if a galaxy was present, add the coords to the list
                if(galaxyPresent) galaxyCoords.add(Pair(lineIndex, i.toLong()))
            }

            // add the current line to the map and continue with the next one
            galaxyMap.add(line)
            lineIndex++
        }

        // get the dimension of the map
        val numLines = galaxyMap.size
        val numColumns = galaxyMap[0].size

        // check for every empty line
        for(i in 0..<numLines) {
            if(galaxyCoords.any { it.first == i.toLong() }) continue
            emptyLines.add(i)
        }

        // check for every empty column
        for(i in 0..<numColumns) {
            if(galaxyCoords.any { it.second == i.toLong() }) continue
            emptyCols.add(i)
        }
    }

    override fun generateFirstSubTaskResult(): String {
        return calculateDistances(2).toString()
    }

    override fun generateSecondSubTaskResult(): String {
        return calculateDistances(1000000).toString()
    }


    /** Calculates the sum of distances based on the specified expansion value (How much space expanded)
     * @param expansion How many spaces actually should be present instead of the only one recorded
     * @return The sum of the distances between all galaxies
     */
    private fun calculateDistances(expansion: Long) : Long {

        // make a copy of our coordinates to leave them intact
        val expandedCoords = galaxyCoords.toMutableList()

        // virtually insert a column by increasing the column values
        for(newColumn in emptyCols.reversed()) {
            for(i in expandedCoords.indices) {
                if(expandedCoords[i].second <= newColumn) continue
                expandedCoords[i] = Pair(expandedCoords[i].first, expandedCoords[i].second + expansion - 1)
            }
        }

        // also virtually increase all the row index of all coords under the new line
        for(newLine in emptyLines.reversed()) {
            for(i in expandedCoords.indices) {
                if(expandedCoords[i].first <= newLine) continue
                expandedCoords[i] = Pair(expandedCoords[i].first + expansion - 1, expandedCoords[i].second)
            }
        }

        // get the distance between all the pairs
        var sumDistance = 0.toLong()
        for(i in expandedCoords.indices) {
            for (j in (i+1)..<expandedCoords.size) {
                val start = expandedCoords[i]
                val end = expandedCoords[j]
                val stepsNeeded = abs(end.second - start.second) + abs(end.first - start.first)
                sumDistance += stepsNeeded
            }
        }

        return sumDistance
    }
}