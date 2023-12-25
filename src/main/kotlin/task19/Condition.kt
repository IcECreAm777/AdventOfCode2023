package task19

/** A condition used inside a workflow */
class Condition(conditionString: String) {

    private var attribute = '-'
    private var lessThen = true
    private var threshold = -1
    private var successMethod = ConditionEvaluationResult.FAILED
    private var nextWorkflow = ""

    init {
        // get the comparison method
        lessThen = conditionString.contains("<")

        // get if the string has a condition or is the last one for the owning workflow
        val condResultSplit = conditionString.split(':')
        if(condResultSplit.size == 1) {
            initializeEvaluationSuccess(condResultSplit[0])
        }
        // get what to do when a condition was specified
        else {
            initializeEvaluationSuccess(condResultSplit[1])

            // get the attribute and the threshold
            val attributeValueSplit = condResultSplit[0].split("<", ">")
            attribute = attributeValueSplit[0][0]
            threshold = attributeValueSplit[1].toInt()
        }
    }

    /** Evaluates this condition for the specified gear */
    fun evaluateCondition(gear: Gear) : Pair<ConditionEvaluationResult, String> {
        val success = Pair(successMethod, nextWorkflow)

        // no actual condition was specified, it is a success everytime
        if(attribute == '-') {
            return success
        }

        // evaluate the condition
        val gearValue = gear.getAttribute(attribute)!!
        val isSuccess = if(lessThen) gearValue < threshold else gearValue > threshold
        return if(isSuccess) success else Pair(ConditionEvaluationResult.FAILED, nextWorkflow)
    }

    /** Initializes what to do when condition was successful */
    private fun initializeEvaluationSuccess(string: String) {
        // get what to do when condition was successful
        if(string.length <= 1) {
            successMethod = if(string[0] == 'A') ConditionEvaluationResult.ACCEPTED
            else ConditionEvaluationResult.REJECTED
            nextWorkflow = ""
        } else {
            successMethod = ConditionEvaluationResult.HANDED_OVER
            nextWorkflow = string
        }
    }
}