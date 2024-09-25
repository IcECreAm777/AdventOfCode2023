package task17

/** A node inside the heat loss graph */
class Node(private val heatLossValue: Int, private val lineIndex: Int, private val colIndex: Int) {

    private var northNeighbour: Node? = null
    private var southNeighbour: Node? = null
    private var eastNeighbour: Node? = null
    private var westNeighbour: Node? = null

    private var isTargetNode = false
    private val memoizationTable = mutableMapOf<Pair<Int, Int>, Int>()


    fun assignNorthNeighbour(neighbour: Node) {
        northNeighbour = neighbour
    }

    fun assignSouthNeighbour(neighbour: Node) {
        southNeighbour = neighbour
    }

    fun assignEastNeighbour(neighbour: Node) {
        eastNeighbour = neighbour
    }

    fun assignWestNeighbour(neighbour: Node) {
        westNeighbour = neighbour
    }

    fun markAsTargetNode() {
        isTargetNode = true
    }


    /** Gets the coordinates of this node inside the graph */
    fun getCoordinates() = Pair(lineIndex, colIndex)

    fun getShortestPath(incomingDirection: Pair<Int, Int>) : Int {
        // no additional way needed to reach the target node
        if(isTargetNode) return 0

        // don't recalculate the shortest path for the incoming direction if we already have it
        if(memoizationTable.contains(incomingDirection)) return memoizationTable[incomingDirection]!!

        TODO("Need to figure out a pathfinding algorithm")
    }
}