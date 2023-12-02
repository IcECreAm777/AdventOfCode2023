import java.io.File

open class Task(inFileName:String, outFileName: String) {

    protected val inputFileName = inFileName
    private val resultFileName = outFileName

    /** Invokes the task functionality and writes the result into the specified output file */
    fun invokeTask() {
        // do the first sub-task
        val first = generateFirstSubTaskResult()
        println("First Sub-Task Result: $first")

        // do the second sub-task
        val second = generateSecondSubTaskResult()
        println("Second Sub-Task Result: $second")

        writeOutput(first, second)
    }

    /** Generates the task result. Is the actual functionality of a task */
    open protected fun generateFirstSubTaskResult() : String {
        return "Task not implemented"
    }

    open protected fun generateSecondSubTaskResult() : String {
        return "Task not implemented"
    }

    /** Writes the results into the output file */
    private fun writeOutput(first: String, second: String) {
        File(resultFileName).writeText("RESULTS\n\nFirst Part: $first " +
                "\n\n-------------------------------------------------------------------------------------------\n\n" +
                "Second Part: $second")
    }
}