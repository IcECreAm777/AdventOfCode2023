import java.io.File

class Task01(inFileName: String, outFileName: String) : Task(inFileName, outFileName) {

    override fun generateFirstSubTaskResult(): String {

        // variable to save the sum into
        var sum = 0

        // go through each line to parse the input
        File(inputFileName).forEachLine {

            // the digits which should be found somewhere
            var first = -1
            var second = -1

            // iterate through the string from front and back
            val length = it.length
            for(i in 0..<length) {

                // try to reassign the digits
                first = if (first < 0 && it[i].isDigit()) it[i].digitToInt() else first
                second = if (second < 0 && it[length - 1 - i].isDigit()) it[length - 1 - i].digitToInt() else second

                // check if both digits where found and break if they are
                if(first >= 0 && second >= 0) break
            }

            // add the found
            sum += first * 10 + second
        }

        // return the result as
        return sum.toString()
    }

    override fun generateSecondSubTaskResult(): String {

        // variable for the sum
        var sum = 0

        // go through each line to parse the input
        File(inputFileName).forEachLine {

            // find either digits or the words for the digits
            val regex = Regex("(?=(\\d|one|two|three|four|five|six|seven|eight|nine))")
            val groups = regex.findAll(it)

            // convert the found groups to digits
            val first = convertToDigit(groups.first().groups[1]!!.value)
            val second = convertToDigit(groups.last().groups[1]!!.value)

            // add the digits to the sum
            sum += first * 10 + second
        }

        // return the sum
        return sum.toString()
    }

    private fun convertToDigit(input: String) : Int {

        // return the number as int when a single digit was found
        if(input.length == 1) {
            return input[0].digitToInt()
        }

        // convert the literal to an int
        return when (input) {
            "one" -> 1
            "two" -> 2
            "three" -> 3
            "four" -> 4
            "five" -> 5
            "six" -> 6
            "seven" -> 7
            "eight" -> 8
            "nine" -> 9
            else -> -1
        }
    }
}