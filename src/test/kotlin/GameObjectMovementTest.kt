import home_work_2.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class GameObjectMovementTest {

    // 1. Успешное перемещение
    @Test
    fun `object moves correctly`() {
        val ship = SpaceShip(Vector2D(12.0, 5.0), Vector2D(-7.0, 3.0), 0.0)
        Mover().move(ship)
        assertEquals(Vector2D(5.0, 8.0), ship.getPosition())
    }

    // 2. Ошибка — невозможно прочитать позицию
    class BrokenMovableNoPosition : Movable {
        override fun getPosition(): Vector2D = throw IllegalStateException("Position not readable")
        override fun getVelocity(): Vector2D = Vector2D(0.0, 1.0)
        override fun setPosition(newPosition: Vector2D) {}
    }

    @Test
    fun `throws if position unreadable`() {
        val obj = BrokenMovableNoPosition()
        val exception = assertThrows(IllegalStateException::class.java) {
            Mover().move(obj)
        }
        assertEquals("Position is not readable", exception.message)
    }

    // 3. Ошибка — невозможно прочитать скорость
    class BrokenMovableNoVelocity : Movable {
        override fun getPosition(): Vector2D = Vector2D(0.0, 0.0)
        override fun getVelocity(): Vector2D = throw IllegalStateException("Velocity not readable")
        override fun setPosition(newPosition: Vector2D) {}
    }

    @Test
    fun `throws if velocity unreadable`() {
        val obj = BrokenMovableNoVelocity()
        val exception = assertThrows(IllegalStateException::class.java) {
            Mover().move(obj)
        }
        assertEquals("Velocity is not readable", exception.message)
    }

    // 4. Ошибка — невозможно установить позицию
    class BrokenMovableCannotSetPosition : Movable {
        override fun getPosition(): Vector2D = Vector2D(1.0, 1.0)
        override fun getVelocity(): Vector2D = Vector2D(1.0, 1.0)
        override fun setPosition(newPosition: Vector2D) {
            throw IllegalStateException("Cannot set position")
        }
    }

    @Test
    fun `throws if position cannot be set`() {
        val obj = BrokenMovableCannotSetPosition()
        val exception = assertThrows(IllegalStateException::class.java) {
            Mover().move(obj)
        }
        assertEquals("Cannot set new position", exception.message?.substringBefore(":"))
    }

    // 5. Поворот объекта
    @Test
    fun `object rotates correctly`() {
        val ship = SpaceShip(Vector2D(0.0, 0.0), Vector2D(0.0, 0.0), 90.0)
        Rotator().rotate(ship, 45.0)
        assertEquals(135.0, ship.getDirectionAngle())
    }

    @Test
    fun `rotation wraps around 360 degrees`() {
        val ship = SpaceShip(Vector2D(0.0, 0.0), Vector2D(0.0, 0.0), 350.0)
        Rotator().rotate(ship, 20.0)
        assertEquals(10.0, ship.getDirectionAngle())
    }


}