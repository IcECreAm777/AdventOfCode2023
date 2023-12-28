package task20

/** Module without any outputs. It returns nothing when trying to process incoming pulses */
class OutputModule(label: String) : Module(label) {
    override fun processIncomingPulse(sender: Module?, pulse: PulseType): Map<Module, PulseType> {
        return mapOf()
    }
}