package task04

import Task
import java.io.File
import kotlin.math.pow
import kotlin.math.roundToInt

/** Class representing the task of day 4 */
class Task04(inFileName: String, outFileName: String) : Task(inFileName, outFileName) {

    // list of cards and the amount of them (amount is only important for the second sub-task)
    private var cards = mutableMapOf<Card, Int>()

    override fun initializeTask() {
        // iterate through each line to parse the input
        File(inputFileName).forEachLine {

            // split the input into winning and play numbers string
            val winningPlaySplit = it.split('|')
            val idWinningSplit = winningPlaySplit[0].split(':')

            // get the ID of the card
            val regex = Regex("\\d+")
            val id = regex.find(idWinningSplit[0])!!.value.toInt()

            // initialize the lists containing the different numbers
            val playNumbers = mutableListOf<Int>()
            val winningNumbers = mutableListOf<Int>()

            // get the values from the input
            parseNumbersString(winningPlaySplit[1], playNumbers)
            parseNumbersString(idWinningSplit[1], winningNumbers)

            // initialize the card and add it to the list
            val card = Card(id, winningNumbers.toList(), playNumbers.toList())
            cards[card] = 1
        }
    }

    override fun generateFirstSubTaskResult(): String {

        // save the result in this var
        var sum = 0

        // iterate through every card to get the points
        for (card in cards.keys) {
            val numMatchingNums = card.getNumMatchingNumbers()
            if(numMatchingNums > 0) sum += 2.0.pow(numMatchingNums - 1).roundToInt()
        }

        // return the result as string
        return sum.toString()
    }

    override fun generateSecondSubTaskResult(): String {

        // iterate through every card
        card@ for(i in 0..<cards.size) {

            // get the current card and the amount of it
            val card = cards.keys.toMutableList()[i]
            val amount = cards[card]!!

            // add a copy for the next n cards based on the amount of winning numbers of the current card
            for(j in 1..card.getNumMatchingNumbers()) {
                if(i+j > cards.size) continue@card
                val cardToUpdate = cards.keys.toMutableList()[i + j]
                cards[cardToUpdate] = cards[cardToUpdate]!! + amount
            }
        }

        // add up the num of cards and return the result
        var sum = 0
        cards.values.forEach { sum += it }
        return sum.toString()
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