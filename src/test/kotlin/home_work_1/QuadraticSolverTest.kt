package home_work_1

import org.bot.home_work_1.QuadraticSolver
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.test.assertTrue

class QuadraticSolverTest {

    private val quadraticSolver by lazy { QuadraticSolver.Base() }

    @Test
    fun `solve should return empty array for x^2 + 1 = 0`() {
        val result = quadraticSolver.solve(1.0, 0.0, 1.0)
        assertTrue(result.isEmpty(), "Expected no real roots")
    }

    @Test
    fun `solve should return two distinct roots for x^2 - 1 = 0`() {
        val result = quadraticSolver.solve(1.0, 0.0, -1.0)
        val expected = arrayOf(1.0, -1.0)

        assertTrue(
            result.sortedArray().contentEquals(expected.sortedArray()), "Expected two roots: 1.0 and -1.0"
        )
    }

    @Test
    fun `solve should return one root with multiplicity 2 for x^2 + 2x + 1 = 0`() {
        val result = quadraticSolver.solve(1.0, 2.0, 1.0)
        val expected = doubleArrayOf(-1.0)

        assertTrue(
            result.size == 1 && kotlin.math.abs(result[0] - expected[0]) < 1e-6, "Expected one root: -1.0"
        )
    }

    @Test
    fun `solve should throw exception if a is zero or nearly zero`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            quadraticSolver.solve(0.0, 2.0, 1.0)
        }
        assert(exception.message!!.contains("a must not be zero"))
    }

    @Test
    fun `solve should return one root when discriminant is very close to zero`() {
        val a = 1.0
        val b = 2.0
        val c = 1.0 - 1e-13

        val result = quadraticSolver.solve(a, b, c)

        // корень должен быть близок к -1.0
        assertEquals(1, result.size)
        assert(abs(result[0] + 1.0) < 1e-6)
    }
}

