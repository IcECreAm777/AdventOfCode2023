package task15

/** represents a box the lenses should be configured inside */
class Box {

    private val lenses = mutableListOf<Lens>()

    /** Removes a lens with the specified label
     * The label of the lens to remove
     */
    fun removeLens(label: String) {
        val toRemove = Lens(label, 0)
        lenses.remove(toRemove)
    }

    /** Adds the lens to the stack if it isn't there or updates the existing one with the new focal length
     * @param lens The lens to add or update
     */
    fun addOrReplaceLens(lens: Lens) {
        // get the index of the lens
        val index = lenses.indexOf(lens)

        // add the lens if it wasn't there already
        if(index < 0) {
            lenses.add(lens)
            return
        }

        // update the lens if there was one already
        lenses[index].focalLength = lens.focalLength
    }

    /** Returns the focusing power of all the lenses inside this box
     * @param index The index of the box inside the box stack
     * @return The sum of all focusing power of the lenses
     */
    fun getFocusingPower(index: Int) : Long {
        var sum = 0.toLong()
        for(lensIndex in lenses.indices) {
            val power = (index + 1) * (lensIndex + 1) * lenses[lensIndex].focalLength
            sum += power
        }
        return sum
    }
}