package home_work_3

import org.bot.home_work_3.Command
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class TestCommandQueue {

    private lateinit var queue: Queue<Command>
    private lateinit var log: MutableList<String>

    @BeforeEach
    fun setup() {
        queue = LinkedList()
        log = mutableListOf()
    }

    // Тестовая команда, которая может завершиться ошибкой
    class FailableCommand(
        private val name: String,
        private val failTimes: Int,
        private val log: MutableList<String>
    ) : Command {
        var executionCount = 0
            private set

        override fun execute() {
            executionCount++
            log.add("execute:$name#$executionCount")
            if (executionCount <= failTimes) {
                throw RuntimeException("Fail $name")
            }
        }

        override fun toString(): String = "FailableCommand($name)"
    }

    // Тестовый LogCommand, пишет в log
    class TestLogCommand(
        private val failedCommand: Command,
        private val exception: Throwable,
        private val log: MutableList<String>
    ) : Command {
        override fun execute() {
            log.add("log:$failedCommand - ${exception.message}")
        }
    }



    // LoggingExceptionHandler → ставит TestLogCommand в очередь
    class TestLoggingHandler(private val log: MutableList<String>) : ExceptionHandler {
        override fun handle(command: Command, exception: Throwable, queue: Queue<Command>) {
            queue.add(TestLogCommand(command, exception, log))
        }
    }

    // Используем RetryCommand/Handler как в основной реализации
    class RetryCommand(
        val original: Command,
        private val attemptsLeft: Int
    ) : Command {
        override fun execute() {
            original.execute()
        }

        fun nextRetry(): RetryCommand = RetryCommand(original, attemptsLeft - 1)
        fun hasAttempts(): Boolean = attemptsLeft > 0
        override fun toString(): String = "Retry($original, left=$attemptsLeft)"
    }

    class RetryHandler(
        private val maxAttempts: Int,
        private val fallback: ExceptionHandler
    ) : ExceptionHandler {
        override fun handle(command: Command, exception: Throwable, queue: Queue<Command>) {
            if (command is RetryCommand) {
                if (command.hasAttempts()) {
                    queue.add(command.nextRetry())
                } else {
                    fallback.handle(command.original, exception, queue)
                }
            } else {
                queue.add(RetryCommand(command, maxAttempts - 1))
            }
        }
    }


    fun process(queue: Queue<Command>, handler: ExceptionHandler) {
        while (queue.isNotEmpty()) {
            val cmd = queue.poll()
            try {
                cmd.execute()
            } catch (t: Throwable) {
                handler.handle(cmd, t, queue)
            }
        }
    }


    @Test
    fun `LogCommand writes exception to log`() {
        val cmd = object : Command {
            override fun execute() = throw RuntimeException("Test error")
            override fun toString() = "TestCommand"
        }
        val logCmd = TestLogCommand(cmd, RuntimeException("Oops"), log)
        logCmd.execute()

        assertTrue(log[0].startsWith("log:TestCommand - Oops"))
    }

    @Test
    fun `LoggingHandler adds log command to queue`() {
        val handler = TestLoggingHandler(log)
        val testCommand = object : Command {
            override fun execute() = throw RuntimeException()
            override fun toString() = "TestCommand"
        }

        handler.handle(testCommand, RuntimeException("fail"), queue)

        assertEquals(1, queue.size)
        assertTrue(queue.peek() is TestLogCommand)
    }

    @Test
    fun `RetryCommand re-executes original command`() {
        val failing = FailableCommand("RetryMe", 1, log)
        val retry = RetryCommand(failing, 1)

        try {
            retry.execute()
        } catch (_: Exception) {
        }

        assertEquals(1, failing.executionCount)
    }

//    @Test
//    fun `RetryHandler retries once then logs`() {
//        val failing = FailableCommand("Cmd", 1, log) // Упадёт 2 раза
//        queue.add(failing)
//
//        val handler = RetryHandler(1, TestLoggingHandler(log))
//        process(queue, handler)
//
//        assertEquals(
//            listOf(
//                "execute:Cmd#1",
//                "execute:Cmd#2",
//                "log:FailableCommand(Cmd) - Fail Cmd"
//            ), log
//        )
//    }



    @Test
    fun `RetryHandler retries twice then logs`() {
        val failing = FailableCommand("Cmd", 3, log)
        queue.add(failing)

        val handler = RetryHandler(2, TestLoggingHandler(log))
        process(queue, handler)

        assertEquals(
            listOf(
                "execute:Cmd#1",
                "execute:Cmd#2",
                "execute:Cmd#3",
                "log:FailableCommand(Cmd) - Fail Cmd"
            ), log
        )
    }

}

