package org.bot.home_work_7

import org.bot.home_work_3.Command

class SoftStopCommand(private val worker: CommandWorker) : Command {

    override fun execute() {
        worker.softStop()
    }

}