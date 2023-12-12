package task11

import Task
import java.io.File
import kotlin.math.abs
import kotlin.math.exp

/** Class for day 11 */
class Task11(inputFileName: String, resultFileName: String) : Task(inputFileName, resultFileName) {

    private val galaxyMap = mutableListOf<MutableList<Boolean>>()
    private var galaxyCoords = mutableListOf<Pair<Int, Int>>()

    override fun initializeTask() {
        // create the initial galaxy map
        var lineIndex = 0
        File(inputFileName).forEachLine {
            // the parsed input for the line
            val line = mutableListOf<Boolean>()

            // check every character for a galaxy
            for (i in it.indices) {
                // check if a galaxy is present (a '#' is there inside the input)
                val galaxyPresent = it[i] == '#'
                line.add(galaxyPresent)

                // if a galaxy was present, add the coords to the list
                if(galaxyPresent) galaxyCoords.add(Pair(lineIndex, i))
            }

            // add the current line to the map and continue with the next one
            galaxyMap.add(line)
            lineIndex++
        }
    }

    override fun generateFirstSubTaskResult(): String {
        // get the dimension of the map
        val numLines = galaxyMap.size
        val numColumns = galaxyMap[0].size

        // check for every empty line
        val emptyLines = mutableListOf<Int>()
        for(i in 0..<numLines) {
            if(galaxyCoords.any { it.first == i }) continue
            emptyLines.add(i)
        }

        // check for every empty column
        val emptyCols = mutableListOf<Int>()
        for(i in 0..<numColumns) {
            if(galaxyCoords.any { it.second == i }) continue
            emptyCols.add(i)
        }

        // virtually insert a column by increasing the column values
        for(newColumn in emptyCols.reversed()) {
            for(i in galaxyCoords.indices) {
                if(galaxyCoords[i].second <= newColumn) continue
                galaxyCoords[i] = Pair(galaxyCoords[i].first, galaxyCoords[i].second + 1)
            }
        }

        // also virtually increase all the row index of all coords under the new line
        for(newLine in emptyLines.reversed()) {
            for(i in galaxyCoords.indices) {
                if(galaxyCoords[i].first <= newLine) continue
                galaxyCoords[i] = Pair(galaxyCoords[i].first + 1, galaxyCoords[i].second)
            }
        }

        // get the distance between all the pairs
        var sumDistance = 0
        for(i in galaxyCoords.indices) {
            for (j in (i+1)..<galaxyCoords.size) {
                val start = galaxyCoords[i]
                val end = galaxyCoords[j]
                val stepsNeeded = abs(end.second - start.second) + abs(end.first - start.first)
                sumDistance += stepsNeeded
            }
        }

        return sumDistance.toString()
    }

    override fun generateSecondSubTaskResult(): String {
        return super.generateSecondSubTaskResult()
    }

}