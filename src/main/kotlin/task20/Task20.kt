package task20

import Task
import java.io.File

/** Task twenty part one and two */
class Task20(inputFileName: String, resultFileName: String) : Task(inputFileName, resultFileName) {

    private var modules = listOf<Module>()
    private var broadcaster: Module? = null

    override fun initializeTask() {
        // iterate through every input line to parse the input
        val initializationMap = mutableMapOf<Module, String>()
        File(inputFileName).forEachLine {
            val trimmed = it.replace(" ", "")
            val split = trimmed.split("->")

            // initialize the current module
            val module = when (split[0][0]) {
                '%' -> FlipFLopModule(split[0].substring(1))
                '&' -> ConjunctionModule(split[0].substring(1))
                else -> Broadcaster(split[0])
            }

            // save the modules for now and their output modules
            initializationMap[module] = split[1]
        }

        // connect the modules
        val endPointModules = mutableListOf<Module>()
        for((module, output) in initializationMap) {
            val moduleNames = output.split(',')
            val outputModules = mutableListOf<Module>()
            for(name in moduleNames) {
                var connectTo = initializationMap.keys.find { it.toString() == name }
                if(connectTo == null) {
                    connectTo = OutputModule(name)
                    endPointModules.add(connectTo)
                }
                outputModules.add(connectTo)
                connectTo.addIncomingModule(module)
            }
            module.assignOutgoingModules(outputModules)
        }

        // save the lists and the broadcaster
        modules = initializationMap.keys.toList() + endPointModules
        broadcaster = modules.find { it.toString() == "broadcaster" }
    }

    override fun generateFirstSubTaskResult(): String {
        var highSignals = 0.toLong()
        var lowSignals = 0.toLong()
        for(i in 0..<1000) {
            val (high, low) = pushButton()
            highSignals += high
            lowSignals += low
        }
        return (highSignals * lowSignals).toString()
    }

    override fun generateSecondSubTaskResult(): String {
        return super.generateSecondSubTaskResult()
    }


    /** Pushes the init button which sends a low pulse to the broadcaster to start a sequence
     * @return a pair which contains the amount of all the pulses send
     */
    private fun pushButton() : Pair<Int, Int> {

        // initialize the first pulses to send from the broadcaster
        var pulseQueue = mutableMapOf<Module, Map<Module, PulseType>>()
        pulseQueue[broadcaster!!] = broadcaster!!.processIncomingPulse(null, PulseType.LOW)

        // initialize the amount of pulses
        var numHigh = pulseQueue[broadcaster]!!.values.count { it == PulseType.HIGH }
        var numLow = 1 + pulseQueue[broadcaster]!!.values.count { it == PulseType.LOW }

        // continue to process impulses one after the other as long as possible
        while (pulseQueue.isNotEmpty()) {
            val nextQueue = mutableMapOf<Module, Map<Module, PulseType>>()
            for((sender, map) in pulseQueue) {
                for((module, pulse) in map) {
                    val processed = module.processIncomingPulse(sender, pulse)
                    nextQueue[module] = processed
                    numHigh += processed.values.count { it == PulseType.HIGH }
                    numLow += processed.values.count { it == PulseType.LOW }
                }
            }
            pulseQueue = nextQueue
        }

        return Pair(numHigh, numLow)
    }
}