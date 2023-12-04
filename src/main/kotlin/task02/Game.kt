package task02

/** Class representing a game were the elf pulls a certain amount of cubes */
class Game(ID: Int, gameSets: List<String>) {

    // private members
    private val id = ID
    private var sets = listOf<List<Pull>>()

    // init code for the primary constructor
    init {

        val listOfSets = mutableListOf<List<Pull>>()

        gameSets.forEach {

            // the list of pulls for this set
            val set = mutableListOf<Pull>()

            // iterate through every single pull to get which cube was pulled how many times
            val pulls = it.split(',')
            for (pull in pulls) {

                // find how many were pulled
                val regex = Regex("\\d+")
                val num = regex.find(pull)!!.value.toInt()

                // find which color was pulled
                var color = CubeColor.INVALID
                if(pull.contains("red")) color = CubeColor.RED
                if(pull.contains("blue")) color = CubeColor.BLUE
                if(pull.contains("green")) color = CubeColor.GREEN

                set.add(Pull(color, num))
            }

            // add it to the list of this game
            listOfSets.add(set.toList())
        }

        sets = listOfSets.toList()
    }

    fun getID() : Int {
        return id
    }

    fun getSets() : List<List<Pull>> {
        return sets
    }
}