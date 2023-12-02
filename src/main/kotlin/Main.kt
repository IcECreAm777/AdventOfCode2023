fun main(args: Array<String>) {

    task01()

}

/** Runs the first task */
fun task01() {
    println("Doing first task...")
    val task01 = Task01("./Tasks/Task01_Input.txt", "./Results/Task01.txt")
    task01.invokeTask()
    println("First task finished")
}
