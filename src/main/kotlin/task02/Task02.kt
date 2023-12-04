package task02

import Task
import java.io.File

/** Task implementation for the second day */
class Task02(inFileName: String, outFileName: String) : Task(inFileName, outFileName) {

    // define how many cubes of each color are possible
    private val maxRed = 12
    private val maxGreen = 13
    private val maxBlue = 14


    override fun generateFirstSubTaskResult(): String {

        // variable saving the result of this sub-task
        var sum = 0

        // iterate through every line to parse the input
        File(inputFileName).forEachLine {

            // variable for the result of this game
            var gamePossible = true

            // get the game ID
            val idAndSetSplit = it.split(':')
            val gameId = getGameId(idAndSetSplit[0])

            // iterate through all the different game sets
            val gameSets = idAndSetSplit[1].split(';')
            loop@ for (set in gameSets) {

                // iterate through each pull made during this set
                val pulls = set.split(',')
                for (pull in pulls) {
                    // check if the pull is possible
                    val (color, num) = parseCubePull(pull)
                    gamePossible = checkPull(color, num)

                    // we can break when this game isn't possible
                    if(!gamePossible) break@loop
                }
            }

            if(gamePossible) sum += gameId
        }

        // return the result as string
        return sum.toString()
    }

    override fun generateSecondSubTaskResult(): String {

        // variable saving the result of this sub-task
        var sum = 0

        // iterate through every line to parse the input
        File(inputFileName).forEachLine {

            // map to save the min amount of cubes
            val colorMap = mutableMapOf(CubeColor.RED to 1, CubeColor.GREEN to 1, CubeColor.BLUE to 1)

            // iterate through all the different game sets
            val idAndSetSplit = it.split(':')
            val gameSets = idAndSetSplit[1].split(';')
            for (set in gameSets) {
                // iterate through each pull made during this set
                val pulls = set.split(',')
                for (pull in pulls) {
                    // update the result dictionary
                    val (color, num) = parseCubePull(pull)
                    colorMap[color] = if(num > colorMap[color]!!) num else colorMap[color]!!
                }
            }

            // calculate the power of this game
            var power = 1
            for(min in colorMap.values) {
                val value = if(min == Int.MAX_VALUE) 1 else min
                power *= value
            }

            // add the power to the result
            sum += power
        }

        // return the result as string
        return sum.toString()
    }


    /** Should get a game ID string (like "Game 11") to parse the containing game ID
     * @param idString A game ID string (like "Game 11")
     * @return The game ID as integer
     */
    private fun getGameId(idString: String) : Int {
        val regex = Regex("\\d+")
        val id = regex.find(idString)
        return id!!.value.toInt()
    }

    /** Parses a single pull into the color and the num of cubes pulled
     * @param pull The string for a single pull (like "5 red")
     * @return CubeColor and NumPulled
     */
    private fun parseCubePull(pull: String) : Pull {

        // find how many were pulled
        val regex = Regex("\\d+")
        val num = regex.find(pull)!!.value.toInt()

        // find which color was pulled
        var color = CubeColor.INVALID
        if(pull.contains("red")) color = CubeColor.RED
        if(pull.contains("blue")) color = CubeColor.BLUE
        if(pull.contains("green")) color = CubeColor.GREEN

        // return the pair
        return Pull(color, num)
    }

    /** Checks if it's possible to pull that many cubes of the specified color
     * @param color Color to check for
     * @param num The number of cubes pulled
     * @return If this pull is valid or not
     */
    private fun checkPull(color: CubeColor, num: Int) : Boolean {
        return when (color) {
            CubeColor.RED -> num <= maxRed
            CubeColor.BLUE -> num <= maxBlue
            CubeColor.GREEN -> num <= maxGreen
            else -> false
        }
    }
}