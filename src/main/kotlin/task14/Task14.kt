package task14

import Task
import java.io.File

/** Class used to determine the results of day 14 */
class Task14(inputFileName: String, resultFileName: String) : Task(inputFileName, resultFileName) {

    private val defaultConfig = mutableListOf<MutableList<Stone>>()

    override fun initializeTask() {
        File(inputFileName).forEachLine {
            val lineConfig = mutableListOf<Stone>()
            for (index in it.indices) {
                lineConfig.add(translateCharToEnum(it[index]))
            }
            defaultConfig.add(lineConfig)
        }
    }

    override fun generateFirstSubTaskResult(): String {

        // make a copy to keep the default configuration
        val mutableConfig = defaultConfig.toMutableList()

        // iterate through every line for the sliding
        for(lineIndex in mutableConfig.indices) {
            // iterate through every column
            for (colIndex in mutableConfig[lineIndex].indices) {
                // make sure a loose stone is encountered
                val currentSpace = mutableConfig[lineIndex][colIndex]
                if(currentSpace != Stone.LOOSE) continue

                // move the stone to its new place
                val (newLine, column) = getSlidingCoordinates(Pair(lineIndex, colIndex), mutableConfig)
                mutableConfig[lineIndex][colIndex] = Stone.EMPTY
                mutableConfig[newLine][column] = Stone.LOOSE
            }
        }

        // iterate again for the weight
        var sumWeight = 0
        val numLines = mutableConfig.size
        for (lineIndex in mutableConfig.indices) {
            val numStones = mutableConfig[lineIndex].count { it == Stone.LOOSE }
            sumWeight += (numLines - lineIndex) * numStones
        }

        // return the weight as our result
        return sumWeight.toString()
    }

    override fun generateSecondSubTaskResult(): String {
        return super.generateSecondSubTaskResult()
    }


    private fun translateCharToEnum(char: Char) : Stone {
        return when (char) {
            'O' -> Stone.LOOSE
            '#' -> Stone.SOLID
            else -> Stone.EMPTY        }
    }

    private fun getSlidingCoordinates(startCoordinates: Pair<Int, Int>, map: MutableList<MutableList<Stone>>)
    : Pair<Int, Int> {
        // init the coordinates used
        val column = startCoordinates.second
        var line = startCoordinates.first - 1

        // look for the empty space possible up north
        while(line >= 0 && map[line][column] == Stone.EMPTY) {
            line--
        }

        return Pair(line + 1, column)
    }
}