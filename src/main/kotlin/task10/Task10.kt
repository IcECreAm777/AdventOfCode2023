package task10

import Task
import java.io.File

/** Class for the 10th Task */
class Task10(inputFileName: String, resultFileName: String) : Task(inputFileName, resultFileName) {

    private val maze = mutableListOf<List<PipeType>>()
    private var startIndex = Pair(0, 0)

    override fun initializeTask() {

        var lineIndex = 0

        // iterate through the lines to parse the input
        File(inputFileName).forEachLine {
            // parse a line to get the pipes per line
            val pipes = mutableListOf<PipeType>()
            for(i in it.indices) {
                // translate the character to the pipe type
                val currentChar = it[i]
                pipes.add(translateCharacterToPipeType(currentChar))

                // assign the start index when found
                if(currentChar == 'S') startIndex = Pair(lineIndex, i)
            }

            // prepare the next iteration and save the result
            maze.add(pipes)
            lineIndex++
        }
    }

    override fun generateFirstSubTaskResult(): String {

        // save the current index for each direction while traversing the loop
        var forwardDirection = Pair(-1, -1)
        var backwardsDirection = Pair(-1, -1)

        // check the upper pipe if it leads into the start pipe
        if(startIndex.first > 0) {
            if(maze[startIndex.first - 1][startIndex.second] == PipeType.SOUTH_WEST
                || maze[startIndex.first - 1][startIndex.second] == PipeType.SOUTH_EAST
                || maze[startIndex.first - 1][startIndex.second] == PipeType.VERTICAL) {
                forwardDirection = Pair(startIndex.first - 1, startIndex.second)
            }
        }

        // check the downwards pipe if it leads to the start pipe
        if(startIndex.first < maze.size) {
            if(maze[startIndex.first + 1][startIndex.second] == PipeType.NORTH_WEST
                    || maze[startIndex.first + 1][startIndex.second] == PipeType.NORTH_EAST
                    || maze[startIndex.first + 1][startIndex.second] == PipeType.VERTICAL) {
                if(forwardDirection.first == -1)
                    forwardDirection = Pair(startIndex.first + 1, startIndex.second)
                else
                    backwardsDirection = Pair(startIndex.first + 1, startIndex.second)
            }
        }

        // if still both are not assigned, check left pipe
        if(backwardsDirection.first == -1 && startIndex.second > 0) {
            if(maze[startIndex.first][startIndex.second - 1] == PipeType.NORTH_EAST
                    || maze[startIndex.first][startIndex.second - 1] == PipeType.SOUTH_EAST
                    || maze[startIndex.first][startIndex.second - 1] == PipeType.HORIZONTAL) {
                if(forwardDirection.first == -1)
                    forwardDirection = Pair(startIndex.first, startIndex.second - 1)
                else
                    backwardsDirection = Pair(startIndex.first, startIndex.second - 1)
            }
        }

        // if still both are not assigned, check right pipe
        if(backwardsDirection.first == -1 && startIndex.second < maze[0].size) {
            if(maze[startIndex.first][startIndex.second + 1] == PipeType.NORTH_WEST
                    || maze[startIndex.first][startIndex.second + 1] == PipeType.SOUTH_WEST
                    || maze[startIndex.first][startIndex.second + 1] == PipeType.HORIZONTAL) {
                if(forwardDirection.first == -1)
                    forwardDirection = Pair(startIndex.first, startIndex.second + 1)
                else
                    backwardsDirection = Pair(startIndex.first, startIndex.second + 1)
            }
        }

        // loop variables
        var numSteps = 1
        var cachedForwardCoords = startIndex
        var cachedBackwardCoords = startIndex

        // traverse the maze until they end uo at the same location
        while(forwardDirection != backwardsDirection) {
            // calculate the next coordinates
            val nextForwardCoords = traverseMaze(cachedForwardCoords, forwardDirection)
            val nextBackwardCoords = traverseMaze(cachedBackwardCoords, backwardsDirection)

            // cache the previous values
            cachedForwardCoords = forwardDirection
            forwardDirection = nextForwardCoords
            cachedBackwardCoords = backwardsDirection
            backwardsDirection = nextBackwardCoords

            // count the steps
            numSteps++
        }

        return numSteps.toString()
    }

    override fun generateSecondSubTaskResult(): String {
        return super.generateSecondSubTaskResult()
    }


    /** Translates a character from the input string to a PipeType
     * @param char The character to convert
     * @return The type of the corresponding character
     */
    private fun translateCharacterToPipeType(char: Char): PipeType {
        return when (char) {
            '|' -> PipeType.VERTICAL
            '-' -> PipeType.HORIZONTAL
            'L' -> PipeType.NORTH_EAST
            'J' -> PipeType.NORTH_WEST
            '7' -> PipeType.SOUTH_WEST
            'F' -> PipeType.SOUTH_EAST
            'S' -> PipeType.START
            else -> PipeType.NONE
        }
    }

    /** Gets the coordinates of the next pipe based on the incoming direction
     * @param prevCoords The incoming direction (previous pipe coords) to get the outward direction
     * @param nextCoords The pipe to calculate the next coordinates from
     * @return The coordinates of the next pipe
     */
    private fun traverseMaze(prevCoords: Pair<Int, Int>, nextCoords: Pair<Int, Int>): Pair<Int, Int> {
        val (first, second) = getCoordinateOffset(maze[nextCoords.first][nextCoords.second])
        val firstCoords = Pair(nextCoords.first + first.first, nextCoords.second + first.second)
        val secondCoords = Pair(nextCoords.first + second.first, nextCoords.second + second.second)
        return if(firstCoords == prevCoords) secondCoords else firstCoords
    }

    /** Gets the possible offsets of the coordinates based on the specified pipe type */
    private fun getCoordinateOffset(type: PipeType) : Pair<Pair<Int, Int>, Pair<Int, Int>> {
        return when (type) {
            PipeType.VERTICAL -> Pair(Pair(1, 0), Pair(-1, 0))
            PipeType.HORIZONTAL -> Pair(Pair(0, 1), Pair(0, -1))
            PipeType.NORTH_EAST -> Pair(Pair(-1, 0), Pair(0, 1))
            PipeType.NORTH_WEST -> Pair(Pair(-1, 0), Pair(0, -1))
            PipeType.SOUTH_WEST -> Pair(Pair(1, 0), Pair(0, -1))
            PipeType.SOUTH_EAST -> Pair(Pair(1, 0), Pair(0, 1))
            else -> Pair(Pair(0, 0), Pair(0, 0))
        }
    }
}