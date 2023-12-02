fun main(args: Array<String>) {

    // get which task(s) to run
    val all = args.isEmpty()
    val task = if(!all && args[0].length == 1 && args[0][0].isDigit()) args[0][0].digitToInt() else 0

    if(all) println("Doing all tasks in a row...")

    // run the tasks
    if(all || task == 1) task01()
    if(all || task == 2) task02()
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
