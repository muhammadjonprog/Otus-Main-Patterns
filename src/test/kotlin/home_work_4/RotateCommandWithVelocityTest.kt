package home_work_4

import home_work_2.SpaceShip
import home_work_2.Vector2D
import org.bot.home_work_4.command.RotateCommandWithVelocity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RotateCommandWithVelocityTest {

    private fun assertVectorEquals(expected: Vector2D, actual: Vector2D, delta: Double = 0.0001) {
        assertEquals(expected.x, actual.x, delta)
        assertEquals(expected.y, actual.y, delta)
    }

    @Test
    fun `should rotate ship angle`() {
        val ship = SpaceShip(
            position = Vector2D(0.0, 0.0),
            velocity = Vector2D(1.0, 0.0),
            angle = 0.0
        )

        val command = RotateCommandWithVelocity(ship, 90.0)
        command.execute()

        assertEquals(90.0, ship.getDirectionAngle(), 0.0001)
    }

    @Test
    fun `should rotate velocity vector if not zero`() {
        val ship = SpaceShip(
            position = Vector2D(0.0, 0.0),
            velocity = Vector2D(1.0, 0.0), // along X
            angle = 0.0
        )

        val command = RotateCommandWithVelocity(ship, 90.0)
        command.execute()

        // Velocity should now point along Y
        assertVectorEquals(Vector2D(0.0, 1.0), ship.getVelocity())
    }

    @Test
    fun `should not change zero velocity vector`() {
        val ship = SpaceShip(
            position = Vector2D(0.0, 0.0),
            velocity = Vector2D(0.0, 0.0),
            angle = 45.0
        )

        val command = RotateCommandWithVelocity(ship, 90.0)
        command.execute()

        assertVectorEquals(Vector2D(0.0, 0.0), ship.getVelocity())
        assertEquals(135.0, ship.getDirectionAngle(), 0.0001)
    }

    @Test
    fun `angle should wrap over 360 degrees`() {
        val ship = SpaceShip(
            position = Vector2D(0.0, 0.0),
            velocity = Vector2D(1.0, 0.0),
            angle = 350.0
        )

        val command = RotateCommandWithVelocity(ship, 20.0)
        command.execute()

        // Expected angle = 10.0
        assertEquals(10.0, ship.getDirectionAngle(), 0.0001)
    }

}