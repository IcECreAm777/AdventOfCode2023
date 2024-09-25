import task01.Task01
import task02.Task02
import task03.Task03
import task04.Task04
import task05.Task05
import task06.Task06
import task07.Task07
import task08.Task08
import task09.Task09
import task10.Task10
import task11.Task11
//import task12.Task12
import task13.Task13
import task14.Task14
import task15.Task15
import task16.Task16
import task17.Task17
import task19.Task19
import task20.Task20

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
    if(all || task ==  1) task01()
    if(all || task ==  2) task02()
    if(all || task ==  3) task03()
    if(all || task ==  4) task04()
    if(all || task ==  5) task05()
    if(all || task ==  6) task06()
    if(all || task ==  7) task07()
    if(all || task ==  8) task08()
    if(all || task ==  9) task09()
    if(all || task == 10) task10()
    if(all || task == 11) task11()
    //if(all || task == 12) task12()
    if(all || task == 13) task13()
    if(all || task == 14) task14()
    if(all || task == 15) task15()
    if(all || task == 16) task16()
    if(all || task == 17) task17()
    if(all || task == 19) task19()
    if(all || task == 20) task20()
}

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

fun task10() {
    println("Doing 10th task...")
    val task = Task10("./src/main/kotlin/task10/Task10_Input.txt", "./Results/Task10.txt")
    task.invokeTask()
    println("10th task finished")
}

fun task11() {
    println("Doing 11th task...")
    val task = Task11("./src/main/kotlin/task11/Task11_Input.txt", "./Results/Task11.txt")
    task.invokeTask()
    println("11th task finished")
}

/*
fun task12() {
    println("Doing 12th task...")
    val task = Task12("./src/main/kotlin/task12/Task12_Input.txt", "./Results/Task12.txt")
    task.invokeTask()
    println("12th task finished")
}*/

fun task13() {
    println("Doing 13th task...")
    val task = Task13("./src/main/kotlin/task13/Task13_Input.txt", "./Results/Task13.txt")
    task.invokeTask()
    println("13th task finished")
}

fun task14() {
    println("Doing 14th task...")
    val task = Task14("./src/main/kotlin/task14/Task14_Input.txt", "./Results/Task14.txt")
    task.invokeTask()
    println("14th task finished")
}

fun task15() {
    println("Doing 15th task...")
    val task = Task15("./src/main/kotlin/task15/Task15_Input.txt", "./Results/Task15.txt")
    task.invokeTask()
    println("15th task finished")
}

fun task16() {
    println("Doing 16th task...")
    val task = Task16("./src/main/kotlin/task16/Task16_Input.txt", "./Results/Task16.txt")
    task.invokeTask()
    println("16th task finished")
}

fun task17() {
    println("Doing 17th task...")
    val task = Task17("./src/main/kotlin/task17/Task17_Input.txt", "./Results/Task17.txt")
    task.invokeTask()
    println("17th task finished")
}

fun task19() {
    println("Doing 19th task...")
    val task = Task19("./src/main/kotlin/task19/Task19_Input.txt", "./Results/Task19.txt")
    task.invokeTask()
    println("19th task finished")
}

fun task20() {
    println("Doing 20th task...")
    val task = Task20("./src/main/kotlin/task20/Task20_Input.txt", "./Results/Task20.txt")
    task.invokeTask()
    println("20th task finished")
}
