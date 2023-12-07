package task07

/** Class representing a hand during the game and its properties when parsing it */
abstract class Hand(protected val string: String, private val bid: Int) {

    /** Returns the type of this hand based on the rules */
    abstract fun getType() : HandType

    /** Returns an artificial value of the character cards based on the rules
     * @param card The card to convert. Should only be (T, J, Q, K, A)
     */
    protected abstract fun characterStrength(card: Char) : Int


    override fun toString(): String {
        return string
    }

    fun getBid(): Int {
        return bid
    }

    fun getFirstCardStrength() : Int {
        return convertStrength(string[0])
    }
    fun getSecondCardStrength() : Int {
        return convertStrength(string[1])
    }
    fun getThirdCardStrength() : Int {
        return convertStrength(string[2])
    }
    fun getFourthCardStrength() : Int {
        return convertStrength(string[3])
    }
    fun getFifthCardStrength() : Int {
        return convertStrength(string[4])
    }

    /** Gets a character and converts it to a numeric strength value
     * @param card The card character to convert the strength for
     * @return The strength based on the rules
     */
    private fun convertStrength(card: Char) : Int {
        // convert the numeric value if possible
        val convertedStrength = card.digitToIntOrNull()
        if(convertedStrength != null) return convertedStrength

        // return an artificial value based on the rules if a character was passed
        return characterStrength(card)
    }


    /** Iterates through the cards and counts them
     * @param cards The map the cards and the amount of them will be saved into
     */
    protected fun initializeCardMap(cards: MutableMap<Char, Int>) {
        for(i in string.indices) {
            // get the current card
            val card = string[i]

            // add the amount of cards when it is a duplicate
            if(cards.keys.contains(card)) {
                cards[card] = cards[card]!! + 1
                continue
            }

            // add a new entry otherwise
            cards[card] = 1
        }
    }
}