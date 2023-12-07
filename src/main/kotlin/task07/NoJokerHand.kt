package task07

/** Hand class used for part one were J are actual cards and not jokers */
class NoJokerHand(string: String, bid: Int) : Hand(string, bid) {

    override fun characterStrength(card: Char): Int {
        return when (card) {
            'T' -> 10
            'J' -> 11
            'Q' -> 12
            'K' -> 13
            'A' -> 14
            else -> 0
        }
    }

    override fun getType(): HandType {
        // get the cards and their amount from the card string
        val cards = mutableMapOf<Char, Int>()
        initializeCardMap(cards)

        // return the type based on the parsed cards
        return when (cards.keys.size) {
            4 -> HandType.ONE_PAIR
            3 -> if(cards.values.contains(3)) HandType.THREE else HandType.TWO_PAIR
            2 -> if(cards.values.contains(4)) HandType.FOUR else HandType.FULL_HOUSE
            1 -> HandType.FIVE
            else -> HandType.HIGH_CARD
        }
    }
}