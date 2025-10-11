package home_work_6

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AdapterGeneratorTest {

    @Test
    fun `генератор должен создавать класс с правильным кодом`() {
        val code = AdapterGenerator.generateAdapterCode(IMovable::class)

        assertTrue(code.contains("class IMovableAdapter"))
        assertTrue(code.contains("override fun getPosition"))
        assertTrue(code.contains("override fun setPosition"))
        assertTrue(code.contains("override fun getVelocity"))
        assertTrue(code.contains("override fun finish"))
    }

    @Test
    fun `IoC должен вызываться для геттера`() {
        val expected = Vector(10, 20)
        IoC.register("spaceship.operations.IMovable:position.get") { _ -> expected }

        val result: Vector = IoC.resolve("spaceship.operations.IMovable:position.get", Any())
        assertEquals(expected, result)
    }

    @Test
    fun `IoC должен вызываться для сеттера`() {
        var called = false
        IoC.register("spaceship.operations.IMovable:position.set") { args ->
            object : ICommand {
                override fun execute() {
                    called = true
                }
            }
        }

        val cmd: ICommand = IoC.resolve("spaceship.operations.IMovable:position.set", Any(), Vector(1, 2))
        cmd.execute()

        assertTrue(called)
    }

    @Test
    fun `IoC должен вызываться для метода finish`() {
        var finished = false
        IoC.register("spaceship.operations.IMovable:finish") { args ->
            object : ICommand {
                override fun execute() {
                    finished = true
                }
            }
        }

        val cmd: ICommand = IoC.resolve("spaceship.operations.IMovable:finish", Any())
        cmd.execute()

        assertTrue(finished)
    }
}
