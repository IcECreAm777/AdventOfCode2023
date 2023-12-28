package task20

/** Module which remembers the state of incoming module pulses */
class ConjunctionModule(label: String) : Module(label) {

    private val pulseMemory = mutableMapOf<Module, PulseType>()

    override fun addIncomingModule(module: Module) {
        super.addIncomingModule(module)
        pulseMemory[module] = PulseType.LOW
    }

    override fun processIncomingPulse(sender: Module?, pulse: PulseType): Map<Module, PulseType> {
        // make sure a sender was specified
        val outputMap = mutableMapOf<Module, PulseType>()
        if(sender == null || !pulseMemory.keys.contains(sender)) return outputMap

        // update the pulse memory map
        pulseMemory[sender] = pulse

        // create the output map based on the memorized pulses
        val outPulse = if(pulseMemory.values.all { it == PulseType.HIGH }) PulseType.LOW else PulseType.HIGH
        for(module in outputModules) {
            outputMap[module] = outPulse
        }
        return outputMap
    }
}