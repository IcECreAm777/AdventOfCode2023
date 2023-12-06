package task06

import Task
import java.io.File

/***/
class Task06(inFileName: String, outFileName: String) : Task(inFileName, outFileName) {

    private var races = mutableMapOf<Int, Int>()

    override fun initializeTask() {

        // parse all the times and distances from the input
        val lines = File(inputFileName).readLines()
        val regex = Regex("\\d+")
        val times = regex.findAll(lines[0]).toList()
        val distances = regex.findAll(lines[1]).toList()

        // create the dictionary
        for (i in times.indices) {
            val time = times[i].value.toInt()
            val distance = distances[i].value.toInt()
            races[time] = distance
        }
    }

    override fun generateFirstSubTaskResult(): String {

        // the result when multiplying the amount of ways to win together
        var product = 1

        // iterate through the times to calculate the amount of possible ways to win
        for((time, distance) in races) {

            var numWaysToWin = 0

            // check for each amount of time holding the button (i) if the record can be broken
            for(i in 1..time) {
                val timeRemaining = time - i
                val distanceTraveled = timeRemaining * i // i is the speed of the boat as well

                if(distanceTraveled > distance) numWaysToWin++
            }

            product *= numWaysToWin
        }

        // return the product as string
        return product.toString()
    }

    override fun generateSecondSubTaskResult(): String {
        return super.generateSecondSubTaskResult()
    }

}