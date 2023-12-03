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
}

/** Runs the first task */
fun task01() {
    println("Doing first task...")
    val task01 = Task01("./Tasks/Task01_Input.txt", "./Results/Task01.txt")
    task01.invokeTask()
    println("First task finished")
}

fun task02() {
    println("Doing second task...")
    val task02 = Task02("./Tasks/Task02_Input.txt", "./Results/Task02.txt")
    task02.invokeTask()
    println("Second task finished")
}

fun task03() {
    println("Doing third task...")
    val task03 = Task03("./Tasks/Task03_Input.txt", "./Results/Task03.txt")
    task03.invokeTask()
    println("Third task finished")
}
