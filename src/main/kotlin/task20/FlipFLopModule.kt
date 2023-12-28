package task20

class FlipFLopModule(label: String) : Module(label) {

    private var on = false

    override fun processIncomingPulse(sender: Module?, pulse: PulseType): Map<Module, PulseType> {
        // return empty map for a high pulse since they are ignored
        val outputMap = mutableMapOf<Module, PulseType>()
        if(pulse == PulseType.HIGH) return outputMap

        // flip the current state and send a signal accordingly
        on = !on
        for(module in outputModules) {
            outputMap[module] = if(on) PulseType.HIGH else PulseType.LOW
        }
        return outputMap
    }
}