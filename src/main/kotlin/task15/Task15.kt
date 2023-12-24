package task15

import Task
import java.io.File

/** Calculating the results for task 15 */
class Task15(inputFileName: String, resultFileName: String) : Task(inputFileName, resultFileName) {

    private var instructions = listOf<String>()

    override fun initializeTask() {
        File(inputFileName).forEachLine {
            instructions = it.split(',')
        }
    }

    override fun generateFirstSubTaskResult(): String {
        var sum = 0
        for(instruction in instructions) {
            sum += hashFun(instruction)
        }
        return sum.toString()
    }

    override fun generateSecondSubTaskResult(): String {
        return super.generateSecondSubTaskResult()
    }


    /** Gets a string and returns the calculated hash value based on the algorithm inside the task */
    private fun hashFun(string: String) : Int {
        var hashValue = 0

        // iterate through every character of the string
        for(index in string.indices) {
            // Determine the ASCII code for the current character of the string.
            val ascii = string[index].code

            // Increase the current value by the ASCII code you just determined.
            hashValue += ascii

            // Set the current value to itself multiplied by 17.
            hashValue *= 17

            // Set the current value to the remainder of dividing itself by 256.
            hashValue %= 256
        }

        return hashValue
    }
}