package org.bot.home_work_7

import org.bot.home_work_3.Command
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.thread

class CommandWorker(
    private val queue: LinkedBlockingQueue<Command>
) : Thread() {

    private val running = AtomicBoolean(true)
    private val accepting = AtomicBoolean(true)

    override fun run() {
        println("Worker $name started.")
        while (running.get()) {
            try {
                val command = queue.take()
                command.execute()
            } catch (e: InterruptedException) {
                if (!running.get()) break
            } catch (e: Exception) {
                println("Error in $name: ${e.message}")
            }
        }
        println("Worker $name stopped.")
    }

    fun submit(command: Command) {
        if (accepting.get()) {
            queue.put(command)
        } else {
            println("Queue of $name no longer accepting commands.")
        }
    }

    fun hardStop() {
        println("Hard stop for $name")
        running.set(false)
        this.interrupt()
    }

    fun softStop() {
        println("Soft stop for $name")
        accepting.set(false)
        Thread {
            while (queue.isNotEmpty()) {
                sleep(100L)
            }
            running.set(false)
            this.interrupt()
        }.start()
    }

}