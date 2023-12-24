package task15

/** Represents a labeled lens used inside the boxes */
data class Lens(val label: String, var focalLength: Int) {

    override fun equals(other: Any?): Boolean {
        val casted = other as Lens
        return label == casted.label
    }

    override fun hashCode(): Int {
        var result = label.hashCode()
        result = 31 * result + focalLength
        return result
    }

}
