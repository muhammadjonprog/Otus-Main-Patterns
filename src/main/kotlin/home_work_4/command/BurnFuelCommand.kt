package org.bot.home_work_4.command

import org.bot.home_work_4.fuel_system.FuelSystem
import org.bot.home_work_3.Command
import org.bot.home_work_4.exception.CommandException

class BurnFuelCommand(private val fuelSystem: FuelSystem, private val fuelCost: Int) : Command {

    override fun execute() {
        if (fuelCost <= 0) throw CommandException("Значение fuelCost не должно быть нулевым!")
        fuelSystem.consume(fuelCost)
    }

}