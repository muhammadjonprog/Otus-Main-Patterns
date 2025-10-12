package home_work_7

import org.bot.home_work_3.Command
import org.bot.home_work_7.CommandWorker
import org.bot.home_work_7.HardStopCommand
import org.bot.home_work_7.SoftStopCommand
import org.bot.home_work_7.StartCommand
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.LinkedBlockingQueue
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CommandWorkerTest {

    private lateinit var queue: LinkedBlockingQueue<Command>
    private lateinit var worker: CommandWorker

    @BeforeEach
    fun setUp() {
        queue = LinkedBlockingQueue()
        worker = CommandWorker(queue).apply { name = "TestWorker" }
    }

    @AfterEach
    fun tearDown() {
        if (worker.isAlive) worker.hardStop()
    }

    @Test
    fun `start command should launch worker thread`() {
        val start = StartCommand(worker)

        start.execute()

        // Проверяем, что поток действительно запущен
        Thread.sleep(100)
        assertTrue(worker.isAlive, "Worker thread should be running after StartCommand")
    }

    @Test
    fun `hard stop should immediately terminate worker thread`() {
        val start = StartCommand(worker)
        start.execute()
        Thread.sleep(100)

        val stop = HardStopCommand(worker)
        stop.execute()

        worker.join(500)
        assertFalse(worker.isAlive, "Worker should be stopped after HardStopCommand")
    }

    @Test
    fun `soft stop should wait until queue becomes empty`() {
        val start = StartCommand(worker)
        start.execute()

        // Команда, которая занимает 500мс
        worker.submit(object : Command {
            override fun execute() {
                Thread.sleep(500)
            }
        })

        val stop = SoftStopCommand(worker)
        stop.execute()

        // ждём до 100 мс, чтобы поток начал обрабатывать задачу
        Thread.sleep(100)
        assertTrue(worker.isAlive, "Worker should still be alive while executing task")

        // ждём максимум 2 сек до завершения (для стабильности)
        worker.join(2000)
        assertFalse(worker.isAlive, "Worker should stop gracefully after all tasks complete")
    }

    @Test
    fun `exceptions in command should not stop worker`() {
        val start = StartCommand(worker)
        start.execute()

        worker.submit(object : Command {
            override fun execute() {
                throw RuntimeException("test exception")
            }
        })
        worker.submit(object : Command {
            override fun execute() {
                println("This should still run")
            }
        })

        Thread.sleep(300)

        assertTrue(worker.isAlive, "Worker should continue running after exception")
    }
}
