package task07

/** Class used for the second part, where J are Jokers */
class JokerHand(string: String, bid: Int) : Hand(string, bid) {

    override fun getType(): HandType {
        // get the cards and their amount from the card string
        val cards = mutableMapOf<Char, Int>()
        initializeCardMap(cards)

        // add the Js to the highest amount of cards and remove the Js ( they don't count as own cards anymore
        if(cards.keys.contains('J')) {
            // edge case when only J are present
            if(cards['J']!! >= 5) return HandType.FIVE

            // get the value of Js and remove them
            val j = cards['J']!!
            cards.remove('J')

            // get the cards with the biggest amount and add the amount of Js to them
            val max = cards.maxBy { it.value }
            cards[max.key] = max.value + j
        }

        // return the type based on the parsed cards
        return when (cards.keys.size) {
            4 -> HandType.ONE_PAIR
            3 -> if(cards.values.contains(3)) HandType.THREE else HandType.TWO_PAIR
            2 -> if(cards.values.contains(4)) HandType.FOUR else HandType.FULL_HOUSE
            1 -> HandType.FIVE
            else -> HandType.HIGH_CARD
        }
    }

    override fun characterStrength(card: Char): Int {
        return when (card) {
            'T' -> 10
            'J' -> 1
            'Q' -> 12
            'K' -> 13
            'A' -> 14
            else -> 0
        }
    }
}