package home_work_4

import org.bot.home_work_4.command.CheckFuelCommand
import org.bot.home_work_4.exception.CommandException
import org.bot.home_work_4.fuel_system.FuelSystem
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class CheckFuelCommandTest {

    @Test
    fun `should not throw if fuel is enough`() {
//        val moveCommand = MoveCommand(SpaceShip(Vector2D(12.0, 5.0), Vector2D(-7.0, 3.0), 0.0))
        val command = CheckFuelCommand(fuelSystem = FuelSystem(100), fuelCost = 50)
        assertDoesNotThrow {
            command.execute()
        }
    }

    @Test
    fun `should throw CommandException if fuel is insufficient`() {
        val command = CheckFuelCommand(fuelSystem = FuelSystem(20), fuelCost = 50)

        val exception = assertThrows<CommandException> {
            command.execute()
        }

        assert(exception.message!!.contains("Недостаточно топлива"))

    }

}