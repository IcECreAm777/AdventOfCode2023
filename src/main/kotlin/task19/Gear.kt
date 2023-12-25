package task19

class Gear(gearString: String) {

    private val attributes = mutableMapOf<Char, Int>()

    init {
        val trimmed = gearString.replace("{", "").replace("}", "")
        val attributeSplit = trimmed.split(',')
        for(attribute in attributeSplit) {
            val nameValueSplit = attribute.split('=')
            attributes[nameValueSplit[0][0]] = nameValueSplit[1].toInt()
        }
    }

    /** Gets the value of the specified attribute */
    fun getAttribute(char: Char) = attributes[char]

    /** Gets the sum of all attributes as the total value of this gear */
    fun getTotalValue() = attributes.values.reduce { acc, int -> acc + int }
}