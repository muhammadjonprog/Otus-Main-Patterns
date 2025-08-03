package home_work_4

import home_work_2.SpaceShip
import home_work_2.Vector2D
import org.bot.home_work_4.command.BurnFuelCommand
import org.bot.home_work_4.command.CheckFuelCommand
import org.bot.home_work_4.command.MacroCommand
import org.bot.home_work_4.command.MoveCommand
import org.bot.home_work_4.fuel_system.FuelSystem
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class MacroCommandTest {

    @Test
    fun `should execute all commands if no exception`() {
        val fuelSystem = FuelSystem(fuel = 10)
        val checkFuelCommand = CheckFuelCommand(fuelSystem = fuelSystem, 10)
        val moveCommand = MoveCommand(spaceShip = SpaceShip(Vector2D(12.0, 5.0), Vector2D(-7.0, 3.0), 0.0))
        val burnFuelCommand = BurnFuelCommand(fuelSystem = fuelSystem, 10)
        val macro = MacroCommand(listOf(checkFuelCommand, moveCommand, burnFuelCommand))

        assertDoesNotThrow {
            macro.execute()
        }
    }

}