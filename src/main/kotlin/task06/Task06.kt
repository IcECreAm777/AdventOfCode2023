package task06

import Task
import java.io.File

/** Class representing task 06 */
class Task06(inFileName: String, outFileName: String) : Task(inFileName, outFileName) {

    override fun generateFirstSubTaskResult(): String {

        // parse all the times and distances from the input
        val lines = File(inputFileName).readLines()
        val regex = Regex("\\d+")
        val times = regex.findAll(lines[0]).toList()
        val distances = regex.findAll(lines[1]).toList()

        // create the dictionary
        val races = mutableMapOf<Int, Int>()
        for (i in times.indices) {
            val time = times[i].value.toInt()
            val distance = distances[i].value.toInt()
            races[time] = distance
        }

        // the result when multiplying the amount of ways to win together
        var product = 1

        // iterate through the times to calculate the amount of possible ways to win
        for((time, distance) in races) {
            product *= getNumWaysToWin(time.toLong(), distance.toLong())
        }

        // return the product as string
        return product.toString()
    }

    override fun generateSecondSubTaskResult(): String {
        // get the time and distance when trimming all the whitespaces
        val lines = File(inputFileName).readLines()
        val time = lines[0].split(':')[1].replace(" ", "").toLong()
        val distance = lines[1].split(':')[1].replace(" ", "").toLong()

        // since there is only one race here, we can return the result directly
        return getNumWaysToWin(time, distance).toString()
    }


    /** Calculates the number of ways to win for the specified time and distance of a race
     * @param time The time the race will last
     * @param distance The distance to beat
     * @return The amount of ways to beat the distance
     */
    private fun getNumWaysToWin(time: Long, distance: Long) : Int {
        // result variable
        var numWaysToWin = 0

        // check for each amount of time holding the button (i) if the record can be broken
        for(i in 1..time) {
            val timeRemaining = time - i
            val distanceTraveled = timeRemaining * i // i is the speed of the boat as well

            if(distanceTraveled > distance) numWaysToWin++
        }

        return numWaysToWin
    }
}