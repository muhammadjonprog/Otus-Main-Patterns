package org.bot.home_work_4.command

import org.bot.home_work_3.Command
import org.bot.home_work_4.exception.CommandException

class MacroCommand(private val commands: List<Command>) : Command {
    override fun execute() {
        commands.forEach { command: Command ->
            try {
                command.execute()
            } catch (e: CommandException) {
                throw CommandException("MacroCommand failed on ${command.javaClass.simpleName}: ${e.message}")
            }

        }
    }

}