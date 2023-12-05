package task05

class ConversionMap(private val source: Long, private val destination: Long, private val range: Long) {

    /** Checks for the given key if it is in range of this conversion map */
    fun isSourceInRange(inSource: Long) : Boolean {
        return inSource >= source && inSource < source + range
    }

    /** Takes the incoming source value and translates it to the destination value.
     * @param inSource The source value being translated
     * @return The destination value. Equals the source key if its not part of this conversion map
     */
    fun translateSourceToDestination(inSource: Long) : Long {

        // return the incoming value if it is not part of this conversion map
        if(!isSourceInRange(inSource)) return inSource

        // calculate the destination value and return it otherwise
        val offset = inSource - source
        return destination + offset
    }
}