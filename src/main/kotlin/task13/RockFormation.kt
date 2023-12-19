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
        // transpose the formation
        val transposedFormation = mutableListOf<String>()
        for(colIndex in formation[0].indices) {
            var colString = ""
            for(rowIndex in formation.indices) {
                colString += formation[rowIndex][colIndex]
            }
            transposedFormation.add(colString)
        }

        // run a check onn the transposed list
        return checkForSymmetry(transposedFormation)
    }


    /** Checks in the specified list of strings if there is a mirror between the lines
     * @param list The list of strings to check
     * @return The index of the line (index with 1) were the mirror is located or 0 if no mirror was found
     */
    private fun checkForSymmetry(list: List<String>) : Int {
        // iterate through the formation
        lines@ for (index in 0..<list.size-1) {
            // check for every offset if a mirrored configuration can be found
            for(offset in 0..<list.size-1) {
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
}