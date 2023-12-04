package task02

import Task
import java.io.File

/** Task implementation for the second day */
class Task02(inFileName: String, outFileName: String) : Task(inFileName, outFileName) {

    // define how many cubes of each color are possible
    private val maxRed = 12
    private val maxGreen = 13
    private val maxBlue = 14

    // list of games parsed from the input
    private var games = listOf<Game>()


    override fun initializeTask() {

        // mutable list to initialize the games
        val gameInit = mutableListOf<Game>()

        // iterate through every line to parse the input
        File(inputFileName).forEachLine {

            // get the game ID
            val idAndSetSplit = it.split(':')
            val gameId = getGameId(idAndSetSplit[0])

            // iterate through all the different game sets
            val gameSets = idAndSetSplit[1].split(';')

            // initialize the game and add it to the list
            val game = Game(gameId, gameSets)
            gameInit.add(game)
        }

        // save the list as immutable list
        games = gameInit.toList()
    }

    override fun generateFirstSubTaskResult(): String {

        // variable saving the result of this sub-task
        var sum = 0

        // iterate through the games
        for(game in games) {

            // variable for the result of this game
            var gamePossible = true

            // iterate through the game sets containing the different pulls to check if everything is possible
            loop@ for(set in game.getSets()) {
                for ((color, num) in set) {
                    gamePossible = checkPull(color, num)

                    // we can break when this game isn't possible
                    if(!gamePossible) break@loop
                }
            }

            // add the game ID to the result
            if(gamePossible) sum += game.getID()
        }

        // return the result as string
        return sum.toString()
    }

    override fun generateSecondSubTaskResult(): String {

        // variable saving the result of this sub-task
        var sum = 0

        // iterate through the games to look for the amount of cubes
        for(game in games) {

            // map to save the min amount of cubes
            val colorMap = mutableMapOf(CubeColor.RED to 1, CubeColor.GREEN to 1, CubeColor.BLUE to 1)

            // look in every set to find the amount of cubes needed and update the min amount if needed
            for(set in game.getSets()) {
                for((color, num) in set) {
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