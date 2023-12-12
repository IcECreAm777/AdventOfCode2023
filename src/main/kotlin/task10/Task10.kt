package task10

import Task
import java.io.File

/** Class for the 10th Task */
class Task10(inputFileName: String, resultFileName: String) : Task(inputFileName, resultFileName) {

    private val maze = mutableListOf<MutableList<PipeType>>()
    private var startIndex = Pair(0, 0)
    private val loopCoords = mutableListOf<Pair<Int, Int>>()

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

        // get the initial directions
        var (forwardDirection, backwardsDirection) = getInitialDirections()
        replaceStartTile(forwardDirection, backwardsDirection)

        // add the initial coords to the list
        loopCoords.add(startIndex)
        loopCoords.add(forwardDirection)
        loopCoords.add(backwardsDirection)

        // loop variables
        var cachedForwardCoords = startIndex
        var cachedBackwardCoords = startIndex

        // traverse the maze until they end up at the same location
        while(forwardDirection != backwardsDirection) {
            // calculate the next coordinates
            val nextForwardCoords = traverseMaze(cachedForwardCoords, forwardDirection)
            val nextBackwardCoords = traverseMaze(cachedBackwardCoords, backwardsDirection)

            // add the calculated coordinates to the list
            loopCoords.add(nextForwardCoords)
            loopCoords.add(nextBackwardCoords)

            // cache the previous values
            cachedForwardCoords = forwardDirection
            forwardDirection = nextForwardCoords
            cachedBackwardCoords = backwardsDirection
            backwardsDirection = nextBackwardCoords
        }

        // the last one will be a duplicate which is removed here
        loopCoords.remove(backwardsDirection)
    }

    override fun generateFirstSubTaskResult(): String {
        // the loop only needs to be traversed half to get to the point farthest away
        val numSteps = loopCoords.size / 2
        return numSteps.toString()
    }

    override fun generateSecondSubTaskResult(): String {

        // variable to save the result
        var fieldsInside = 0

        for(lineIndex in maze.indices) {
            var inside = false

            // get every part of the loop for the current line
            val loopParts = loopCoords.filter { it.first == lineIndex }
            if(loopParts.isEmpty()) continue

            // get the most outer edges for this line
            val maxColIndex = loopParts.maxOf { it.second }
            val minColIndex = loopParts.minOf { it.second }

            // iterate through the edges
            var previousCorner = PipeType.NONE
            column@ for(colIndex in minColIndex..maxColIndex) {
                // get the field
                val field = maze[lineIndex][colIndex]

                // we need to check the next corner to know if we need to switch the inside flag if a corner was hit
                if(previousCorner != PipeType.NONE) {
                    if(!isTileCorner(field)) continue@column
                    inside = if(checkDirections(previousCorner, field)) inside else !inside
                    previousCorner = PipeType.NONE
                    continue@column
                }

                // check if the field is part of the loop
                val partOfLoop = loopParts.any{ it.first == lineIndex && it.second == colIndex }

                // when hitting the loop, act accordingly
                if(partOfLoop) {
                    if(field == PipeType.VERTICAL) inside = !inside
                    if(isTileCorner(field)) previousCorner = field
                    continue@column
                }

                // we can count a vacant tile if its inside at this point
                if(inside) fieldsInside++
            }
        }

        return fieldsInside.toString()
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

    /** Gets a pair of offsets and returns the corresponding pipe types for that */
    private fun getPipeType(offsets: Pair<Pair<Int, Int>, Pair<Int, Int>>) : PipeType {
        return when (offsets) {
            Pair(Pair(1, 0), Pair(-1, 0)) -> PipeType.VERTICAL
            Pair(Pair(-1, 0), Pair(1, 0)) -> PipeType.VERTICAL
            Pair(Pair(0, 1), Pair(0, -1)) -> PipeType.HORIZONTAL
            Pair(Pair(0, -1), Pair(0, 1)) -> PipeType.HORIZONTAL
            Pair(Pair(-1, 0), Pair(0, 1)) -> PipeType.NORTH_EAST
            Pair(Pair(0, 1), Pair(-1, 0)) -> PipeType.NORTH_EAST
            Pair(Pair(-1, 0), Pair(0, -1)) -> PipeType.NORTH_WEST
            Pair(Pair(0, -1), Pair(-1, 0)) -> PipeType.NORTH_WEST
            Pair(Pair(1, 0), Pair(0, -1)) -> PipeType.SOUTH_WEST
            Pair(Pair(0, -1), Pair(1, 0)) -> PipeType.SOUTH_WEST
            Pair(Pair(1, 0), Pair(0, 1)) -> PipeType.SOUTH_EAST
            Pair(Pair(0, 1), Pair(1, 0)) -> PipeType.SOUTH_EAST
            else -> PipeType.NONE
        }
    }

    /** Gets the pipes that lead into the start pipe */
    private fun getInitialDirections() : Pair<Pair<Int, Int>, Pair<Int, Int>> {
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

        // return the directions
        return Pair(forwardDirection, backwardsDirection)
    }

    /** Gets the neighbours of the start tile, checks which type it should be and replaces it with the found type
     * @param forward The first neighbour of the start tile in a direction
     * @param backwards The second neighbour of the start tile in a different direction
     */
    private fun replaceStartTile(forward: Pair<Int, Int>, backwards: Pair<Int, Int>) {
        // get which type is needed to connect the incoming coordinates
        val firstOffset = Pair(startIndex.first - forward.first, startIndex.second - forward.second)
        val secondOffset = Pair(startIndex.first - backwards.first, startIndex.second - backwards.second)
        val startType = getPipeType(Pair(firstOffset, secondOffset))

        // replace the start tile
        maze[startIndex.first][startIndex.second] = startType
    }

    /** Checks if the specified pipe is a corner piece */
    private fun isTileCorner(type: PipeType) : Boolean {
        return type == PipeType.NORTH_WEST || type == PipeType.NORTH_EAST || type == PipeType.SOUTH_EAST
                || type == PipeType.SOUTH_WEST
    }

    /** Checks if the specified pipes look to the same direction or not*/
    private fun checkDirections(first: PipeType, second: PipeType) : Boolean {
        return first == PipeType.SOUTH_EAST && second == PipeType.SOUTH_WEST
                || first == PipeType.NORTH_EAST && second == PipeType.NORTH_WEST
    }
}