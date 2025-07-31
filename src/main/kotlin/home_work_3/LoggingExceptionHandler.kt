package home_work_3

import org.bot.home_work_3.Command
import java.util.*

class LoggingExceptionHandler : ExceptionHandler {

    override fun handle(command: Command, exception: Throwable, queue: Queue<Command>) {
        queue.add(LogCommand(command, exception))
    }

}
