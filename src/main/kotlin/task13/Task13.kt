package task13

import Task
import java.io.File

class Task13(inputFileName: String, resultFileName: String) : Task(inputFileName, resultFileName) {

    private val formations = mutableListOf<RockFormation>()

    override fun initializeTask() {
        var rockFormation = RockFormation()

        // iterate through every line to parse the input
        val reader = File(inputFileName).bufferedReader()
        var line = reader.readLine()
        while (line != null) {
            // finish the current rock formation when encountering an empty line
            if(line.isEmpty()) {
                formations.add(rockFormation)
                rockFormation = RockFormation()
                line = reader.readLine()
                continue
            }

            // add the current line to the current formation
            rockFormation.addLine(line)
            line = reader.readLine()
        }

        // add the last rock formation to the list since it wasn't done inside the loop
        formations.add(rockFormation)
    }

    override fun generateFirstSubTaskResult(): String {

        var sum = 0

        // iterate through every formation to get the score for this task
        for (formation in formations) {
            val row = formation.getNumReflectedRows() * 100
            val columns = formation.getNumReflectedColumns()
            sum += row + columns
        }

        return sum.toString()
    }

    override fun generateSecondSubTaskResult(): String {
        return super.generateSecondSubTaskResult()
    }

}