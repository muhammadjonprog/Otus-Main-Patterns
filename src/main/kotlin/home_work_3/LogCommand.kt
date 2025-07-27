package home_work_3

import org.bot.home_work_3.Command

class LogCommand(
    private val failedCommand: Command,
    private val exception: Throwable
) : Command {

    override fun execute() {
        println("ЛОГ: Команда ${failedCommand::class.simpleName} завершилась с ошибкой: ${exception.message}")
    }

}