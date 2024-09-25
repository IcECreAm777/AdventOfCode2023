package task19

import Task
import java.io.File

/** Invokes task 19 */
class Task19(inputFileName: String, resultFileName: String) : Task(inputFileName, resultFileName) {

    private val workflows = mutableListOf<Workflow>()
    private val gears = mutableListOf<Gear>()

    override fun initializeTask() {
        // iterate through every line to parse the input
        var readWorkflows = true
        val reader = File(inputFileName).bufferedReader()
        var line = reader.readLine()
        while (line != null) {
            // switch context when encountering an empty line
            if(line.isEmpty()) {
                readWorkflows = false
                line = reader.readLine()
                continue
            }

            // read the line as workflow
            if(readWorkflows) {
                val newWorkflow = Workflow(line)
                workflows.add(newWorkflow)
                line = reader.readLine()
                continue
            }

            // read the line as gear
            val newGear = Gear(line)
            gears.add(newGear)
            line = reader.readLine()
        }
    }

    override fun generateFirstSubTaskResult(): String {

        // loop variables
        val acceptedGears = mutableListOf<Gear>()
        val startWorkflow = workflows.find { it.toString() == "in" }!!

        // put every gear through the workflows
        for(gear in gears) {
            var currentState = ConditionEvaluationResult.HANDED_OVER
            var currentWorkflow = startWorkflow
            while (currentState != ConditionEvaluationResult.REJECTED) {
                // evaluate the gear for the current workflow
                val (result, nextWorkflow) = currentWorkflow.evaluateGear(gear)
                currentState = result

                // accept the gear when the state was reached
                if(currentState == ConditionEvaluationResult.ACCEPTED) {
                    acceptedGears.add(gear)
                    break
                }

                // get the next workflow if specified
                if(nextWorkflow.isEmpty()) continue
                currentWorkflow = workflows.find{ it.toString() == nextWorkflow }!!
            }
        }

        // calculate the result of the accepted gears as our result
        var sum = 0
        for(accepted in acceptedGears) {
            sum += accepted.getTotalValue()
        }
        return sum.toString()
    }

    override fun generateSecondSubTaskResult(): String {
        return super.generateSecondSubTaskResult()
    }

}