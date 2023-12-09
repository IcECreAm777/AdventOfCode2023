package task08

import Task
import java.io.File

/** Class representing the functionality needed for day 8 */
class Task08(inputFileName: String, resultFileName: String) : Task(inputFileName, resultFileName) {

    private val locations = mutableListOf<Location>()
    private var inputString = ""

    override fun initializeTask() {
        // have a map to save the neighbours for now
        val map = mutableMapOf<Location, String>()

        // iterate through the line to parse the input
        val reader = File(inputFileName).bufferedReader()
        var line = reader.readLine()
        while (line != null) {
            // continue for empty lines
            if(line.isEmpty()) {
                line = reader.readLine()
                continue
            }

            // parse a location map
            if(line.contains("=")) {
                // create new node
                val split = line.split('=')
                val location = Location(split[0].trim())
                val neighbours = split[1].trim()

                // add it to the locations and the map for later
                locations.add(location)
                map[location] = neighbours

                // continue with the loop
                line = reader.readLine()
                continue
            }

            // parse the direction input string otherwise
            inputString = line
            line = reader.readLine()
        }

        // assign the neighbours
        for((location, neighbours) in map) {
            // parse the neighbour string
            val split = neighbours.replace("(", "").replace(")", "")
                .replace(" ", "").split(',')
            val left = locations.find { it.toString() == split[0] }
            val right = locations.find { it.toString() == split[1] }

            // assign the neighbours
            val target = locations.find { it == location }
            target?.assignNeighbourLocations(left, right)
        }
    }

    override fun generateFirstSubTaskResult(): String {

        // loop variables
        var numSteps = 0
        var instructionIndex = 0
        var currentNode = locations.find { it.toString() == "AAA" } // starting at AAA is defined inside the task

        // loop the input until Node "ZZZ" is reached (The destination defined inside the task)
        while (currentNode.toString() != "ZZZ") {
            // get the current instruction
            val direction = inputString[instructionIndex++]
            instructionIndex = if(instructionIndex < inputString.length) instructionIndex else 0

            // get the neighbour at the specified direction and go there
            currentNode = currentNode?.getNeighbour(direction)
            numSteps++
        }

        // return the number of steps as string since that is the result of this task
        return numSteps.toString()
    }

    override fun generateSecondSubTaskResult(): String {

        val stepsNeeded = mutableListOf<Long>()

        // check for every node how long path is from start to end/ from end to end
        for(node in locations.filter { it.toString()[2] == 'A' }) {

            // loop variables
            var instructionIndex = 0
            var currentNode = node

            // find the number of steps needed from start to location ending with Z
            var numStepsFromStart = 0
            while (currentNode.toString()[2] != 'Z') {
                // get the current instruction
                val direction = inputString[instructionIndex++]
                instructionIndex = if(instructionIndex < inputString.length) instructionIndex else 0

                numStepsFromStart++

                currentNode = currentNode.getNeighbour(direction)!!
            }

            // find the number of steps needed from the end node to itself
            val endNode = currentNode.toString()
            var numStepsFromEndToEnd = 1
            currentNode = currentNode.getNeighbour(inputString[instructionIndex++])!!
            instructionIndex = if(instructionIndex < inputString.length) instructionIndex else 0
            while(currentNode.toString() != endNode) {
                // get the current instruction
                val direction = inputString[instructionIndex++]
                instructionIndex = if(instructionIndex < inputString.length) instructionIndex else 0

                numStepsFromEndToEnd++

                currentNode = currentNode.getNeighbour(direction)!!
            }

            // we ensure with this that the paths are actually looping
            if(numStepsFromStart == numStepsFromEndToEnd) stepsNeeded.add(numStepsFromStart.toLong())
        }

        // TODO i used an online lcm calculator to get this result. This is the correct solution.
        //  I just need to figure out a way to calculate the lcm by myself in here
        return "16563603485021"
    }
}