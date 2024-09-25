package task12

/** Class that takes care of a group inside a configuration */
class ConfigurationGroup(private val configuration: String) {

    private val appliedGroups = mutableListOf<Int>()
    private var configured = false

    /** After applying everything, this calculates how many configurations are possible */
    fun getNumPossibleConfigurations() : Int {

        // recursion anchors
        if(appliedGroups.reduce {acc, int -> acc + int} == configuration.length) return 1

        // find the groups of '#' to check if it consumes a group
        val regex = Regex("#+")
        val hashtagGroups = regex.findAll(configuration).toList()

        // no '#' found, try to place all the groups inside the string
        if(hashtagGroups.isEmpty()) {
            // TODO implement
        }

        // for every group found, check if a certain configuration is needed
        for(index in hashtagGroups.indices) {
            // get the amount of springs in the found regex group
            val spring = hashtagGroups[index].value
            val size = spring.length

            // assign a group to it
            val groupToAssign = appliedGroups.first()
            val difference = size - groupToAssign

            // the group was satisfied already
            if(difference == 0) {
                // split the configuration into 2 different ones
                val range = hashtagGroups[index].range
                val satisfiedConfig = configuration.substring(range.first, range.last + 1)
                val restConfig = configuration.substring(range.last + 2)

                // create a child configuration which is satisfied
                val satisfiedConfiguration = ConfigurationGroup(satisfiedConfig)
                satisfiedConfiguration.tryAddGroupOfSize(groupToAssign)
                appliedGroups.remove(groupToAssign)

                // create a new configuration group based on the rest of the configuration string
                val unsatisfiedConfiguration = ConfigurationGroup(restConfig)
                for(group in appliedGroups) {
                    unsatisfiedConfiguration.tryAddGroupOfSize(group)
                }

                // return the sum of both child configurations
                return satisfiedConfiguration.getNumPossibleConfigurations() +
                        unsatisfiedConfiguration.getNumPossibleConfigurations()
            }

            // when there aren't enough '#' to satisfy the requirement
            if(difference <= 0) {

            }
        }

        return 1
    }

    /** Tries to add a group of the specified size
     * @param size The size of the group to add
     * @return True when it's possible to add this size, False if it isn't
     */
    fun tryAddGroupOfSize(size: Int) : Boolean {
        // get how many spaces are already occupied with the needed spaces in-between
        val occupied = if(appliedGroups.isEmpty()) -1
            else appliedGroups.reduce{ acc, int -> acc + int } + appliedGroups.size - 1

        // the group requested is too big for this configuration, so this one is finished
        if(occupied + size + 1 > configuration.length) {
            configured = true
            return false
        }

        // otherwise the size can be added
        appliedGroups.add(size)
        return true
    }

    /** Returns if this groups was already configured or not */
    fun isConfigured() : Boolean {
        return configured
    }
}