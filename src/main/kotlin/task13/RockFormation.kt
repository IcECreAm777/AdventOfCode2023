package task13

/** Class representing a rock formation where the mirrors are being searched in */
class RockFormation {

    private val formation = mutableListOf<String>()


    /** Adds a line to the formation */
    fun addLine(string: String) {
        formation.add(string)
    }

    /** gets the amount of rows that are mirrored */
    fun getNumReflectedRows() : Int {
        return checkForSymmetry(formation)
    }

    /** Gets the amount of columns mirrored */
    fun getNumReflectedColumns() : Int {
        // run a check on the transformed list
        return checkForSymmetry(transformFormation())
    }

    /** Finds the smudge inside this formation and replaces it with the opposite character
     * @param rowToBan The row index to ignore when getting the mirror after fixing smudge
     * @param columnToBan The column index to ignore when getting the mirror after fixing smudge
     * @return The score of the mirror based on the definition in the task
     */
    fun findSmudgedMirror(rowToBan: Int, columnToBan: Int) : Int {

        // fix horizontal smudges first
        val horizontal = findSmudge(formation, rowToBan)
        if(horizontal >= 0) return horizontal * 100

        // fix vertical smudges after that if no horizontal smudge was found
        return findSmudge(transformFormation(), columnToBan)
    }


    /** Returns the transformed formation */
    private fun transformFormation() : List<String> {
        val transposedFormation = mutableListOf<String>()
        for(colIndex in formation[0].indices) {
            var colString = ""
            for(rowIndex in formation.indices) {
                colString += formation[rowIndex][colIndex]
            }
            transposedFormation.add(colString)
        }
        return transposedFormation
    }

    /** Checks in the specified list of strings if there is a mirror between the lines
     * @param list The list of strings to check
     * @return The index of the line (index with 1) were the mirror is located or 0 if no mirror was found
     */
    private fun checkForSymmetry(list: List<String>) : Int {
        // iterate through the formation
        lines@ for (index in 0..<list.size-1) {
            // check for every offset if a mirrored configuration can be found
            offset@ for(offset in 0..<list.size-1) {
                // get the index and check if boundary was reached
                val lineToCheckIndex = index - offset
                val mirroredLineToCheckIndex = index + offset + 1
                if(lineToCheckIndex < 0 || mirroredLineToCheckIndex >= list.size) return index + 1

                // if one isn't equal it can't be mirrored
                if(list[lineToCheckIndex] != list[mirroredLineToCheckIndex]) continue@lines
            }
        }

        // nothing was mirrored at this point
        return 0
    }

    /** Finds a smudge for this formation and returns the coordinates
     * @param list The list too look for a smudge
     * @param bannedIndex The index to ignore if it was found previously (it's not the smudge mirror)
     * @return The mirror index based on the smudge
     */
    private fun findSmudge(list: List<String>, bannedIndex: Int) : Int {
        // iterate through the formation
        lines@ for (index in 0..<list.size-1) {
            // check for every offset if a mirrored configuration can be found
            offset@ for(offset in 0..<list.size-1) {
                // get the index and check if boundary was reached
                val lineToCheckIndex = index - offset
                val mirroredLineToCheckIndex = index + offset + 1
                if(lineToCheckIndex < 0 || mirroredLineToCheckIndex >= list.size) {
                    if(bannedIndex >= 0 && index == bannedIndex) continue@offset
                    return index + 1
                }

                // continue when only one character is different (the smudge)
                val differentIndex = getDifferentIndex(list[lineToCheckIndex], list[mirroredLineToCheckIndex])
                if(differentIndex.size == 1) continue@offset

                // still check other lines if they're symmetrical
                if(differentIndex.isNotEmpty()) continue@lines
            }
        }

        // no smudge was found
        return -1
    }

    /** Gets every index were the two specified strings differ */
    private fun getDifferentIndex(string: String, other: String) : List<Int> {
        val result = mutableListOf<Int>()
        for (index in string.indices) {
            if(string[index] == other[index]) continue
            result.add(index)
        }
        return result
    }
}