package org.bot.home_work_4.command

import org.bot.home_work_4.fuel_system.FuelSystem
import org.bot.home_work_3.Command
import org.bot.home_work_4.exception.CommandException

class CheckFuelCommand(
    private val fuelSystem: FuelSystem, private val fuelCost: Int
) : Command {

    override fun execute() {
        if (!fuelSystem.hasFuel(fuelCost)) {
            throw CommandException("Недостаточно топлива: требуется $fuelCost, доступно ${fuelSystem.getCurrent()}")
        }
    }

}