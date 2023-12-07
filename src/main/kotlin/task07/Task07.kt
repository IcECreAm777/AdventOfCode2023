package task07

import Task
import java.io.File

/** Class representing the tasks of day 7 */
class Task07(inputFileName: String, resultFileName: String) : Task(inputFileName, resultFileName) {

    override fun generateFirstSubTaskResult(): String {

        // initialize the list of hands as no joker hands
        val hands = mutableListOf<NoJokerHand>()

        // iterate through each line to parse the input
        File(inputFileName).forEachLine {
            // parse the input to create a new hand
            val split = it.split(' ')
            hands.add(NoJokerHand(split[0], split[1].toInt()))
        }

        // return the result of the sorted list
        return generateResultForHands(hands).toString()
    }

    override fun generateSecondSubTaskResult(): String {
        // initialize the list of hands as no joker hands
        val hands = mutableListOf<JokerHand>()

        // iterate through each line to parse the input
        File(inputFileName).forEachLine {
            // parse the input to create a new hand
            val split = it.split(' ')
            hands.add(JokerHand(split[0], split[1].toInt()))
        }

        // return the result of the sorted list
        return generateResultForHands(hands).toString()
    }


    /** Takes a list of cards that should be equal in type and sorts them according to the rules ascending
     * @param hands The hands being sorted
     */
    private fun <T: Hand> sortEqualHands(hands: MutableList<T>) {
        hands.sortWith(compareBy({it.getFirstCardStrength()}, {it.getSecondCardStrength()}, {it.getThirdCardStrength()},
            {it.getFourthCardStrength()}, {it.getFifthCardStrength()}))
    }

    /** Takes the list of hands, sorts them by rank and returns the sum of winning amounts based on the bid of the hand
     * @param hands The hands to sort and evaluate
     * @return The sum of winnings
     */
    private fun generateResultForHands(hands: List<Hand>) : Int {

        // init the different lists by types
        val highCards = mutableListOf<Hand>()
        val onePairs = mutableListOf<Hand>()
        val twoPairs = mutableListOf<Hand>()
        val threes = mutableListOf<Hand>()
        val fullHouses = mutableListOf<Hand>()
        val fours = mutableListOf<Hand>()
        val fives = mutableListOf<Hand>()

        for(hand in hands) {
            when (hand.getType()) {
                HandType.HIGH_CARD -> highCards.add(hand)
                HandType.ONE_PAIR -> onePairs.add(hand)
                HandType.TWO_PAIR -> twoPairs.add(hand)
                HandType.THREE -> threes.add(hand)
                HandType.FULL_HOUSE -> fullHouses.add(hand)
                HandType.FOUR -> fours.add(hand)
                HandType.FIVE -> fives.add(hand)
            }
        }

        // sort each list based on the value of cards
        sortEqualHands(highCards)
        sortEqualHands(onePairs)
        sortEqualHands(twoPairs)
        sortEqualHands(threes)
        sortEqualHands(fullHouses)
        sortEqualHands(fours)
        sortEqualHands(fives)

        // put them back together
        val sortedHands = highCards + onePairs + twoPairs + threes + fullHouses + fours + fives

        // get the result by iterating over them and multiplying the bidding
        var sum = 0
        for(i in sortedHands.indices) {
            sum += sortedHands[i].getBid() * (i + 1)
        }
        return sum
    }
}