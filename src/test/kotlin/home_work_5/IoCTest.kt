package home_work_5

import org.bot.home_work_3.Command
import org.junit.jupiter.api.Assertions.*
import kotlin.concurrent.thread
import kotlin.test.Test

class IoCTest {

    @Test
    fun `должен регистрировать и резолвить команду`() {
        IoC.resolve<Command>(
            "IoC.Register",
            "move",
            { args: Array<out Any> ->
                val obj = args[0] as String
                ActionCommand { println("$obj moves forward") }
            } // <- без трейлинг-лямбды вне скобок
        ).execute()

        val cmd = IoC.resolve<Command>("move", "Ship-1")
        assertNotNull(cmd)
        cmd.execute()
    }

    @Test
    fun `должен переключать скоупы`() {
        IoC.resolve<Command>("Scopes.New", "scopeA").execute()
        IoC.resolve<Command>("Scopes.Current", "scopeA").execute()

        IoC.resolve<Command>(
            "IoC.Register",
            "fire",
            { args: Array<out Any> ->
                val obj = args[0] as String
                ActionCommand { println("$obj shoots 🔫") }
            }
        ).execute()

        val cmd = IoC.resolve<Command>("fire", "Ship-A")
        assertNotNull(cmd)
        cmd.execute()
    }

    @Test
    fun `разные скоупы не пересекаются`() {
        // scope1
        IoC.resolve<Command>("Scopes.New", "scope1").execute()
        IoC.resolve<Command>("Scopes.Current", "scope1").execute()

        val output1 = StringBuilder()
        IoC.resolve<Command>(
            "IoC.Register",
            "turn",
            { args: Array<out Any> ->
                ActionCommand { output1.append("turn left") }
            }
        ).execute()

        // scope2
        IoC.resolve<Command>("Scopes.New", "scope2").execute()
        IoC.resolve<Command>("Scopes.Current", "scope2").execute()

        val output2 = StringBuilder()
        IoC.resolve<Command>(
            "IoC.Register",
            "turn",
            { args: Array<out Any> ->
                ActionCommand { output2.append("turn right") }
            }
        ).execute()

        // Проверяем независимость
        IoC.resolve<Command>("Scopes.Current", "scope1").execute()
        val cmd1 = IoC.resolve<Command>("turn")
        cmd1.execute()

        IoC.resolve<Command>("Scopes.Current", "scope2").execute()
        val cmd2 = IoC.resolve<Command>("turn")
        cmd2.execute()

        assertNotEquals(output1.toString(), output2.toString()) // разные поведения
    }

    @Test
    fun `многопоточный тест`() {
        IoC.resolve<Command>("Scopes.New", "thread1").execute()
        IoC.resolve<Command>("Scopes.New", "thread2").execute()

        val results = mutableListOf<String>()

        val t1 = thread {
            IoC.resolve<Command>("Scopes.Current", "thread1").execute()
            IoC.resolve<Command>(
                "IoC.Register",
                "action",
                { args: Array<out Any> -> ActionCommand { results.add("thread1 action") } }
            ).execute()
            IoC.resolve<Command>("action").execute()
        }

        val t2 = thread {
            IoC.resolve<Command>("Scopes.Current", "thread2").execute()
            IoC.resolve<Command>(
                "IoC.Register",
                "action",
                { args: Array<out Any> -> ActionCommand { results.add("thread2 action") } }
            ).execute()
            IoC.resolve<Command>("action").execute()
        }

        t1.join()
        t2.join()

        assertTrue(results.contains("thread1 action"))
        assertTrue(results.contains("thread2 action"))
    }
}