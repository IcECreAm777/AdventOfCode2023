package task17

import Task
import java.io.File

/** Day 17 result calculating class */
class Task17(inputFileName: String, resultFileName: String) : Task(inputFileName, resultFileName) {

    private val graph = mutableListOf<MutableList<Node>>()

    override fun initializeTask() {
        // initialize all the nodes with the corresponding heat values
        var index = 0
        File(inputFileName).forEachLine {
            val line = mutableListOf<Node>()
            for(charIndex in it.indices) {
                line.add(Node(it[charIndex].digitToInt(), index, charIndex))
            }
            graph.add(line)
            index++
        }

        // assign the neighbours
        for(lineIndex in graph.indices) {
            for(colIndex in graph[lineIndex].indices) {
                if(lineIndex - 1 >= 0) graph[lineIndex][colIndex].assignNorthNeighbour(graph[lineIndex-1][colIndex])
                if(lineIndex + 1 < graph.size)
                    graph[lineIndex][colIndex].assignSouthNeighbour(graph[lineIndex+1][colIndex])
                if(colIndex - 1 >= 0) graph[lineIndex][colIndex].assignEastNeighbour(graph[lineIndex][colIndex-1])
                if(colIndex + 1 < graph[lineIndex].size)
                    graph[lineIndex][colIndex].assignWestNeighbour(graph[lineIndex][colIndex+1])
            }
        }

        // mark the last node as target node
        graph.last().last().markAsTargetNode()
    }

    override fun generateFirstSubTaskResult(): String {
        return super.generateFirstSubTaskResult()
    }

    override fun generateSecondSubTaskResult(): String {
        return super.generateSecondSubTaskResult()
    }

}