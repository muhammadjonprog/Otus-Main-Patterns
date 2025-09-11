package home_work_5

import org.bot.home_work_3.Command
import java.util.concurrent.ConcurrentHashMap

// typealias для фабрики, чтобы не писать длинные типы
typealias Factory = (Array<out Any>) -> Any

object IoC {
    // Скоупы: scopeId -> (key -> factory)
    private val scopes = ConcurrentHashMap<String, MutableMap<String, Factory>>()

    // Текущий scope для каждого потока
    private val currentScope = ThreadLocal.withInitial { "root" }

    init {
        // Рутовый скоуп
        scopes["root"] = ConcurrentHashMap()

        // Регистрируем мета-команды
        registerMetaCommands()
    }

    private fun registerMetaCommands() {
        scopes["root"]!!["IoC.Register"] = { args ->
            ActionCommand {
                val key = args[0] as String
                val factory = args[1] as (Array<out Any>) -> Any
                getScope()[key] = factory
            }
        }

        scopes["root"]!!["Scopes.New"] = { args ->
            ActionCommand {
                val scopeId = args[0] as String
                scopes[scopeId] = ConcurrentHashMap()
            }
        }

        scopes["root"]!!["Scopes.Current"] = { args ->
            ActionCommand {
                val scopeId = args[0] as String
                currentScope.set(scopeId)
            }
        }
    }


    private fun getScope(): MutableMap<String, Factory> {
        return scopes[currentScope.get()] ?: throw IllegalStateException("Scope not found")
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> resolve(key: String, vararg args: Any): T {
        val factory = if (key.startsWith("IoC.") || key.startsWith("Scopes.")) {
            scopes["root"]?.get(key)
        } else {
            getScope()[key]
        }

        return factory?.invoke(args) as? T
            ?: throw IllegalStateException("No registration for key $key in scope ${currentScope.get()}")
    }

}

class ActionCommand(private val action: () -> Unit) : Command {
    override fun execute() = action()
}
