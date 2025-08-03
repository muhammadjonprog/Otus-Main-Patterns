package home_work_4

import org.bot.home_work_4.command.BurnFuelCommand
import org.bot.home_work_4.fuel_system.FuelSystem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BurnFuelCommandTest {

    @Test
    fun `should reduce fuel by fuelConsumptionRate`() {
        val fuelSystem = FuelSystem(fuel = 100)
        val command = BurnFuelCommand(fuelSystem = fuelSystem, fuelCost = 30)
        command.execute()
        assertEquals(70, fuelSystem.getCurrent())
    }

}