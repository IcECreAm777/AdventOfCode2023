import java.io.File

/** Class representing the third task */
class Task03(inFileName: String, outFileName: String) : Task(inFileName, outFileName) {

    override fun generateFirstSubTaskResult(): String {

        // variable used to store the result
        var sum = 0

        // read 3 lines simultaneously
        var prevLine: String
        var currentLine = ""
        var nextLine = ""

        // go through each line to parse the input
        File(inputFileName).forEachLine {

            // move along inside the input
            prevLine = currentLine
            currentLine = nextLine
            nextLine = it

            // add the evaluated line to the result
            sum += findPartNumbers(prevLine, currentLine, nextLine)
        }

        // the last line wasn't evaluated right now, so we need to move along the lines one last time
        prevLine = currentLine
        currentLine = nextLine
        nextLine = ""

        // add this to the result as well
        sum += findPartNumbers(prevLine, currentLine, nextLine)

        // return the result as string
        return sum.toString()
    }

    override fun generateSecondSubTaskResult(): String {

        // variable used to store the result
        var sum = 0

        // read 3 lines simultaneously
        var prevLine: String
        var currentLine = ""
        var nextLine = ""

        // go through each line to parse the input
        File(inputFileName).forEachLine {

            // move along inside the input
            prevLine = currentLine
            currentLine = nextLine
            nextLine = it

            // add the evaluated line to the result
            sum += findGearRatio(prevLine, currentLine, nextLine)
        }

        // the last line wasn't evaluated right now, so we need to move along the lines one last time
        prevLine = currentLine
        currentLine = nextLine
        nextLine = ""

        // add this to the result as well
        sum += findGearRatio(prevLine, currentLine, nextLine)

        // return the result as string
        return sum.toString()
    }


    /** Finds a number inside the current line and checks if its part of the result (if a symbol is adjacent)
     * @param prevLine The previous read line. Might be needed to check for symbols.
     * @param currentLine The current line being evaluated
     * @param nextLine The next line. Might be needed to check for symbols.
     * @return The sum of numbers of the current line that are part of the result
     */
    private fun findPartNumbers(prevLine: String, currentLine: String, nextLine: String) : Int {

        // edge cases
        if(currentLine.isEmpty()) return 0

        // find numbers inside the current line
        val numRegex = Regex("\\d+")
        val matches = numRegex.findAll(currentLine)
        if(matches.toList().isEmpty()) return 0

        // keep track of the sum of this line
        var sum = 0

        // check for every match if a symbol is adjacent
        match@ for (match in matches) {

            // get the start and end index used for checking the symbols (clamped for edges)
            val start = if(match.range.first - 1 >= 0) match.range.first - 1 else match.range.first
            val end = if(match.range.last + 1 < currentLine.length) match.range.last + 1 else match.range.last

            // check this line if the previous and next character is a symbol
            if(isSymbol(currentLine[start]) || isSymbol(currentLine[end])) {
                sum += match.value.toInt()
                continue@match
            }

            // check the previous and next line if a symbol is adjacent
            for(i in start..end) {
                if(prevLine.isNotEmpty() && isSymbol(prevLine[i])) {
                    sum += match.value.toInt()
                    continue@match
                }
                if(nextLine.isNotEmpty() && isSymbol(nextLine[i])) {
                    sum += match.value.toInt()
                    continue@match
                }
            }
        }

        // return the sum of all valid numbers of this line
        return sum
    }

    /** Finds and asterisk inside the current line to check if it encloses a gear ratio.
     * @param prevLine The previous read line. Might be needed to check for symbols.
     * @param currentLine The current line being evaluated
     * @param nextLine The next line. Might be needed to check for symbols.
     * @return The sum of gear ratios of all the asterisks inside the current line
     */
    private fun findGearRatio(prevLine: String, currentLine: String, nextLine: String) : Int {

        // edge cases
        if(currentLine.isEmpty()) return 0

        // find the asterisks inside the current line
        val regex = Regex("\\*+")
        val matches = regex.findAll(currentLine)
        if(matches.toList().isEmpty()) return 0

        // the result for the current line
        var sum = 0

        // try to find the gear ratio
        for (match in matches) {

            // the list of integers around the asterisk
            val nums = mutableListOf<Int>()

            // get the start and end index used for checking the symbols (clamped for edges)
            val asteriskIndex = match.range.first
            val start = if(asteriskIndex - 1 >= 0) asteriskIndex - 1 else asteriskIndex
            val end = if(asteriskIndex + 1 < currentLine.length) asteriskIndex + 1 else asteriskIndex

            // check left and right of the asterisk
            if(currentLine[start].isDigit()) nums.add(generateInt(currentLine, start))
            if(currentLine[end].isDigit()) nums.add(generateInt(currentLine, end))

            // look inside the previous and next line for numbers of the gear ratio
            checkLineForInt(prevLine, asteriskIndex, start, end, nums)
            checkLineForInt(nextLine, asteriskIndex, start, end, nums)

            // add the product of the numbers to the result, if there are exactly 2 numbers around the asterisk
            if(nums.size == 2) sum += nums[0] * nums[1]
        }

        return sum
    }

    /** Checks if the specified character is a symbol or not. In this context, '.' doesn't count as symbol as well.
     * @param char The character to evaluate
     * @return If it's a symbol or not
     */
    private fun isSymbol(char: Char) : Boolean {
        return !char.isLetterOrDigit() && char != '.'
    }

    /** Gets the integer at the specified index
     * @param line The line the integers is in
     * @param index The index inside the line to get the integer from
     * @return The converted Integer
     */
    private fun generateInt(line: String, index: Int) : Int {

        // the range of the integer
        var start = index
        var end = index

        // iterate through the string to get the actual range
        while (start >= 0 && line[start].isDigit()) start--
        while (end < line.length && line[end].isDigit()) end++

        // convert the integer
        val intString = line.substring(start + 1, end)
        return intString.toInt()
    }

    /** Checks for an Integer around an asterisk
     * @param line The line to check
     * @param index The index of the asterisk
     * @param left The index of the left corner around the asterisk
     * @param right The index of the right corner around the asterisk
     * @param nums The list of numbers the result will be saved into
     */
    private fun checkLineForInt(line: String, index: Int, left: Int, right: Int, nums: MutableList<Int>) {

        // edge case
        if(line.isEmpty()) return

        // we can check the middle - if it's a digit, both corners belong to the same digit
        if(line[index].isDigit()) {
            nums.add(generateInt(line, index))
            return
        }

        // check corners if middle is empty
        if(line[left].isDigit()) nums.add(generateInt(line, left))
        if(line[right].isDigit()) nums.add(generateInt(line, right))
    }
}