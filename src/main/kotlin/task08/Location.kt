package task08


/** Represents a location inside the desert. Is a node inside a tree */
class Location(private val name: String) {

    private var leftNeighbour: Location? = null
    private var rightNeighbour: Location? = null


    override fun toString() = name


    /** Assigns the neighbours to this location */
    fun assignNeighbourLocations(left: Location?, right: Location?) {
        leftNeighbour = left
        rightNeighbour = right
    }

    /** Gets the neighbour in the specified direction
     * @param direction The direction to take. Should be either 'L' or 'R'
     * @return The location in that direction or null for invalid input
     */
    fun getNeighbour(direction: Char) : Location? {
        return when(direction) {
            'L' -> leftNeighbour
            'R' -> rightNeighbour
            else -> null
        }
    }

}