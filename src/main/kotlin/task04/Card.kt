package task04

/** Class representing a play card for task 04 */
class Card(ID: Int, winningNumbers: List<Int>, playNumbers: List<Int>) {

    private val id = ID
    private val winningNums = winningNumbers
    private val playNums = playNumbers

    fun getID() : Int {
        return id
    }

    fun getWinningNumbers() : List<Int> {
        return winningNums
    }

    fun getPlayNumbers() : List<Int> {
        return playNums
    }

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
