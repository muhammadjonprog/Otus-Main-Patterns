package home_work_6

// IoC.kt
object IoC {
    private val registry = mutableMapOf<String, (Array<out Any?>) -> Any?>()

    fun register(key: String, creator: (Array<out Any?>) -> Any?) {
        registry[key] = creator
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> resolve(key: String, vararg args: Any?): T {
        return registry[key]?.invoke(args) as T
    }
}

// ICommand.kt
interface ICommand {
    fun execute()
}
