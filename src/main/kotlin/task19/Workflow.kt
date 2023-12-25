package task19

/** Class representing a workflow */
class Workflow(workflowString: String) {

    private val label: String
    private val conditions = mutableListOf<Condition>()

    init {
        // get the label of this workflow
        val labelSplit = workflowString.split('{')
        label = labelSplit[0]

        // initialize the workflow queue
        val conditionString = labelSplit[1].replace("}", "")
        val conditionStrings = conditionString.split(',')
        for(condition in conditionStrings) {
            val newCondition = Condition(condition)
            conditions.add(newCondition)
        }
    }

    override fun toString(): String {
        return label
    }

    /** Passes the specified gear through this workflow
     * @return a pair of the result and the next workflow if needed
     */
    fun evaluateGear(gear: Gear) : Pair<ConditionEvaluationResult, String> {
        for(condition in conditions) {
            val resultPair = condition.evaluateCondition(gear)
            if(resultPair.first != ConditionEvaluationResult.FAILED) return resultPair
        }

        return Pair(ConditionEvaluationResult.FAILED, "")
    }
}