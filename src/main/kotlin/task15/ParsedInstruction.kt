package task15

/** Data class to parse an instruction */
data class ParsedInstruction(var label: String, var remove: Boolean, var focalLength: Int) {

    constructor(instruction: String) : this("", false, 0) {
        val split = instruction.split("-" ,"=")
        label = split[0]
        focalLength = if(split[1].isEmpty()) 0 else split[1].toInt()
        remove = instruction.contains('-')
    }

}