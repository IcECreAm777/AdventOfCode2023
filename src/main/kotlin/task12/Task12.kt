package task12

import Task
import java.io.File

/** Class for day 12 */
class Task12(inputFileName: String, resultFileName: String) : Task(inputFileName, resultFileName) {

    override fun generateFirstSubTaskResult(): String {

        // var to track the amount of results
        var sumOfConfigurations = 0

        // iterate through each line to parse the input
        File(inputFileName).forEachLine {
            // get the configuration and the sizes of the groups
            val split = it.split(' ')
            val configuration = split[0]
            val groupSizes = split[1].split(',').map { num -> num.toInt() }

            // split the configuration into groups we need to investigate
            val groups = configuration.split('.').filter { str -> str.isNotEmpty() }

            // create a configuration group for every group defined inside the configuration
            val configurationGroups = mutableListOf<ConfigurationGroup>()
            for(group in groups) {
                val configGroup = ConfigurationGroup(group)
                configurationGroups.add(configGroup)
            }

            // map the specified groups to the created configurations
            for(sizes in groupSizes) {
                val groupsToAddTo = configurationGroups.filter { group -> !group.isConfigured() }
                for (groupToAdd in groupsToAddTo) {
                    if(groupToAdd.tryAddGroupOfSize(sizes)) break
                }
            }

            // add the different nums of configurations to the sum
            var numPossibleConfigs = 1
            for(group in configurationGroups) {
                numPossibleConfigs *= group.getNumPossibleConfigurations()
            }
            sumOfConfigurations += numPossibleConfigs
        }

        return sumOfConfigurations.toString()
    }

    override fun generateSecondSubTaskResult(): String {
        return super.generateSecondSubTaskResult()
    }

}