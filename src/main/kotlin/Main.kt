import task01.Task01
import task02.Task02
import task03.Task03
import task04.Task04
import task05.Task05
import task06.Task06
import task07.Task07
import task08.Task08
import task09.Task09

fun main(args: Array<String>) {

    // get which task(s) to run
    val all = args.isEmpty()
    val task = if(args.size == 1) args[0].toIntOrNull() else null

    // error with program parameters
    if(!all && (task == null || task > 25 || task < 1)) {
        println("Invalid program parameters. Just pass a number from 1 - 25 to specify the task to run or nothing " +
                "at all to run every task.")
        return
    }

    if(all) println("Doing all tasks in a row...")

    // run the tasks
    if(all || task == 1) task01()
    if(all || task == 2) task02()
    if(all || task == 3) task03()
    if(all || task == 4) task04()
    if(all || task == 5) task05()
    if(all || task == 6) task06()
    if(all || task == 7) task07()
    if(all || task == 8) task08()
    if(all || task == 9) task09()
}

/** Runs the first task */
fun task01() {
    println("Doing first task...")
    val task = Task01("./src/main/kotlin/task01/Task01_Input.txt", "./Results/Task01.txt")
    task.invokeTask()
    println("First task finished")
}

fun task02() {
    println("Doing second task...")
    val task = Task02("./src/main/kotlin/task02/Task02_Input.txt", "./Results/Task02.txt")
    task.invokeTask()
    println("Second task finished")
}

fun task03() {
    println("Doing third task...")
    val task = Task03("./src/main/kotlin/task03/Task03_Input.txt", "./Results/Task03.txt")
    task.invokeTask()
    println("Third task finished")
}

fun task04() {
    println("Doing 4th task...")
    val task = Task04("./src/main/kotlin/task04/Task04_Input.txt", "./Results/Task04.txt")
    task.invokeTask()
    println("4th task finished")
}

fun task05() {
    println("Doing 5th task...")
    val task = Task05("./src/main/kotlin/task05/Task05_Input.txt", "./Results/Task05.txt")
    task.invokeTask()
    println("5th task finished")
}

fun task06() {
    println("Doing 6th task...")
    val task = Task06("./src/main/kotlin/task06/Task06_Input.txt", "./Results/Task06.txt")
    task.invokeTask()
    println("6th task finished")
}

fun task07() {
    println("Doing 7th task...")
    val task = Task07("./src/main/kotlin/task07/Task07_Input.txt", "./Results/Task07.txt")
    task.invokeTask()
    println("7th task finished")
}

fun task08() {
    println("Doing 8th task...")
    val task = Task08("./src/main/kotlin/task08/Task08_Input.txt", "./Results/Task08.txt")
    task.invokeTask()
    println("8th task finished")
}

fun task09() {
    println("Doing 9th task...")
    val task = Task09("./src/main/kotlin/task09/Task09_Input.txt", "./Results/Task09.txt")
    task.invokeTask()
    println("9th task finished")
}
