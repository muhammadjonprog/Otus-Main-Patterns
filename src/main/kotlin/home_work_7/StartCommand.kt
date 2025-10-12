package org.bot.home_work_7

import org.bot.home_work_3.Command

class StartCommand(private val worker: CommandWorker) : Command {

    override fun execute() {
        if (!worker.isAlive) {
            worker.start()
        }
    }

}