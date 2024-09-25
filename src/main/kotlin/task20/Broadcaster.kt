package task20

/** The broadcaster module */
class Broadcaster(label: String) : Module(label) {

    override fun processIncomingPulse(sender: Module?, pulse: PulseType): Map<Module, PulseType> {
        // in this case, every connected module will receive a low pulse
        val outputMap = mutableMapOf<Module, PulseType>()
        for(module in outputModules) {
            outputMap[module] = PulseType.LOW
        }
        return outputMap
    }
}