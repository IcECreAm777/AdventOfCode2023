import java.io.File

open class Task(inFileName:String, outFileName: String) {

    protected val inputFileName = inFileName
    private val resultFileName = outFileName

    /** Invokes the task functionality and writes the result into the specified output file */
    fun invokeTask() {

        // initialize data if needed
        initializeTask()

        // do the first sub-task
        val first = generateFirstSubTaskResult()
        println("First Sub-Task Result: $first")

        // do the second sub-task
        val second = generateSecondSubTaskResult()
        println("Second Sub-Task Result: $second")

        writeOutput(first, second)
    }

    /** Generates the task result of the first sub-task. Is the actual functionality of a task.
     * @return The result of the sub-task as String
     */
    protected open fun generateFirstSubTaskResult() : String {
        return "Task not implemented"
    }

    /** Generates the task result of the second sub-task. Is the actual functionality of a task.
     * @return The result of the sub-task as String
     */
    protected open fun generateSecondSubTaskResult() : String {
        return "Task not implemented"
    }

    /** Is being called before the sub-task methods and can be used to initialize data. */
    protected open fun initializeTask() { }

    /** Writes the results into the output file */
    private fun writeOutput(first: String, second: String) {
        File(resultFileName).writeText("RESULTS\n\nFirst Part: $first " +
                "\n\n-------------------------------------------------------------------------------------------\n\n" +
                "Second Part: $second")
    }
}