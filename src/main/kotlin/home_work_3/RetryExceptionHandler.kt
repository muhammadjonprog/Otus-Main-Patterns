package home_work_3

import org.bot.home_work_3.Command
import java.util.*

class RetryExceptionHandler(
    private val maxAttempts: Int,
    private val fallbackHandler: ExceptionHandler
) : ExceptionHandler {

    override fun handle(command: Command, exception: Throwable, queue: Queue<Command>) {
        if (command is RetryCommand) {
            if (command.hasAttempts()) {
                queue.add(command.nextRetry())
            } else {
                fallbackHandler.handle(command, exception, queue)
            }
        } else {
            queue.add(RetryCommand(command, maxAttempts - 1))
        }
    }

}
