package task07

/** Class representing a hand during the game and its properties when parsing it */
class Hand(private val string: String, private val bid: Int) {

    private var type = HandType.HIGH_CARD
    private val strength = Array(5) { 0 }

    init {
        val cards = mutableMapOf<Char, Int>()
        for(i in string.indices) {

            // get the current card and its strength
            val card = string[i]
            strength[i] = convertStrength(card)

            // add the amount of cards when it is a duplicate
            if(cards.keys.contains(card)) {
                cards[card] = cards[card]!! + 1
                continue
            }

            // add a new entry otherwise
            cards[card] = 1
        }

        assignType(cards)
    }


    override fun toString(): String {
        return string
    }

    fun getBid(): Int {
        return bid
    }

    fun getType(): HandType {
        return type
    }

    fun getFirstCardStrength() : Int {
        return strength[0]
    }
    fun getSecondCardStrength() : Int {
        return strength[1]
    }
    fun getThirdCardStrength() : Int {
        return strength[2]
    }
    fun getFourthCardStrength() : Int {
        return strength[3]
    }
    fun getFifthCardStrength() : Int {
        return strength[4]
    }


    /** Takes a map of cards and assigns the type iof this hand based on that.
     * Map should contain the character and its amount inside the hand
     */
    private fun assignType(cards: MutableMap<Char, Int>) {
        type = when (cards.keys.size) {
            4 -> HandType.ONE_PAIR
            3 -> if(cards.values.contains(3)) HandType.THREE else HandType.TWO_PAIR
            2 -> if(cards.values.contains(4)) HandType.FOUR else HandType.FULL_HOUSE
            1 -> HandType.FIVE
            else -> HandType.HIGH_CARD
        }
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
        return when (card) {
            'T' -> 10
            'J' -> 11
            'Q' -> 12
            'K' -> 13
            'A' -> 14
            else -> 1
        }
    }
}