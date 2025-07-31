package home_work_3

import org.bot.home_work_3.Command

class RetryCommand(
    private val original: Command,
    private val attemptsLeft: Int
) : Command {

    override fun execute() {
        original.execute()
    }

    fun nextRetry(): RetryCommand = RetryCommand(original, attemptsLeft - 1)

    fun hasAttempts(): Boolean = attemptsLeft > 0

}
