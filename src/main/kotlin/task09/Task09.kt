package task09

import Task
import java.io.File

/** CLass representing day 9 */
class Task09(inputFileName: String, resultFileName: String) : Task(inputFileName, resultFileName) {

    override fun generateFirstSubTaskResult(): String {
        var sum: Long = 0
        File(inputFileName).forEachLine {
            val nums = it.split(' ').map { str -> str.toLong() }
            sum += extrapolate(nums)
        }
        return sum.toString()
    }

    override fun generateSecondSubTaskResult(): String {
        var sum: Long = 0
        File(inputFileName).forEachLine {
            val nums = it.split(' ').map { str -> str.toLong() }.reversed()
            sum += extrapolate(nums)
        }
        return sum.toString()
    }


    /** Takes a list of numbers and calculates the extrapolated value (the next value in the sequence).
     * @param nums List of the numbers to find the next value for
     * @return The next number inside the sequence
     */
    private fun extrapolate(nums: List<Long>): Long {

        // recursion anchor - no reduction needed when all differences are 0
        if(nums.all { it == 0.toLong() }) return 0

        // get the list of differences first
        val differences = mutableListOf<Long>()
        for(i in 0..<nums.size-1){
            differences.add(nums[i + 1] - nums[i])
        }

        // get the value of the differences list to calculate the next num in sequence
        val subResult = extrapolate(differences)
        return nums.last() + subResult
    }
}