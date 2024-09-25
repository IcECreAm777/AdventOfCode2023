package task20

/** Base class for a module connected by cables */
abstract class Module(private val label: String) {

    protected var outputModules = listOf<Module>()
    protected val incomingModules = mutableListOf<Module>()

    override fun toString(): String {
        return label
    }


    /** Assigns the modules connected to the output of this model */
    fun assignOutgoingModules(modules: MutableList<Module>) {
        outputModules = modules.toList()
    }

    /** Adds a module to the incoming modules */
    open fun addIncomingModule(module: Module) {
        incomingModules.add(module)
    }


    /** Processes the incoming pulse and returns a map which module will receive which kind of pulse afterward */
    abstract fun processIncomingPulse(sender: Module?, pulse: PulseType) : Map<Module, PulseType>
}