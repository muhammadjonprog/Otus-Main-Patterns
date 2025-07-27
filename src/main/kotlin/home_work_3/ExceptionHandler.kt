package home_work_3

import org.bot.home_work_3.Command
import java.util.*

interface ExceptionHandler {
    fun handle(command: Command, exception: Throwable, queue: Queue<Command>)
}