package task04

import Task
import java.io.File
import kotlin.math.pow
import kotlin.math.roundToInt

/** Class representing the task of day 4 */
class Task04(inFileName: String, outFileName: String) : Task(inFileName, outFileName) {

    override fun generateFirstSubTaskResult(): String {

        // save the result in this var
        var sum = 0

        // iterate through each line to parse the input
        File(inputFileName).forEachLine {

            // split the input into winning and play numbers string
            val winningPlaySplit = it.split('|')

            // initialize the lists containing the different numbers
            val playNumbers = mutableListOf<Int>()
            val winningNumbers = mutableListOf<Int>()

            // get the values from the input
            parseNumbersString(winningPlaySplit[1], playNumbers)
            parseNumbersString(winningPlaySplit[0].split(':')[1], winningNumbers)

            // check for every play number if they are also a winning number
            var numMatchingNums = 0
            for (play in playNumbers) {
                if(winningNumbers.contains(play)) numMatchingNums++
            }

            // add the points to the result
            if(numMatchingNums > 0) sum += 2.0.pow(numMatchingNums - 1).roundToInt()
        }

        // return the result as string
        return sum.toString()
    }

    override fun generateSecondSubTaskResult(): String {
        return super.generateSecondSubTaskResult()
    }


    /** Parses a string and returns all numbers inside it
     * @param str The string that will be parsed
     * @param nums A list of Ints the results will be saved in
     */
    private fun parseNumbersString(str: String, nums: MutableList<Int>) {
        // find all numbers inside the input string
        val regex = Regex("\\d+")
        val matches = regex.findAll(str)

        // convert every number to an integer and add it to the result list
        for (match in matches) {
            val int = match.value.toIntOrNull()
            if(int != null) nums.add(int)
        }
    }
}