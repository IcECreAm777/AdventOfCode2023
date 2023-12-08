package task04

/** Class representing a play card for task 04 */
class Card(private val id: Int, private val winningNums: List<Int>, private val playNums: List<Int>) {

    /** Gets the number of play numbers that are also winning numbers */
    fun getNumMatchingNumbers() : Int {
        // check for every play number if they are also a winning number
        var numMatchingNums = 0
        for (play in playNums) {
            if(winningNums.contains(play)) numMatchingNums++
        }

        return numMatchingNums
    }
}
