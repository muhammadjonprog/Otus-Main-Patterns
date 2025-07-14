import org.bot.QuadraticSolver
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class QuadraticSolverInvalidInputTest {

    private val quadraticSolver by lazy { QuadraticSolver.Base() }

    @Test
    fun `solve should throw if a is NaN or Infinite`() {
        assertThrows<IllegalArgumentException> { quadraticSolver.solve(Double.NaN, 1.0, 1.0) }
        assertThrows<IllegalArgumentException> { quadraticSolver.solve(Double.POSITIVE_INFINITY, 1.0, 1.0) }
        assertThrows<IllegalArgumentException> { quadraticSolver.solve(Double.NEGATIVE_INFINITY, 1.0, 1.0) }
    }

    @Test
    fun `solve should throw if b is NaN or Infinite`() {
        assertThrows<IllegalArgumentException> { quadraticSolver.solve(1.0, Double.NaN, 1.0) }
        assertThrows<IllegalArgumentException> { quadraticSolver.solve(1.0, Double.POSITIVE_INFINITY, 1.0) }
        assertThrows<IllegalArgumentException> { quadraticSolver.solve(1.0, Double.NEGATIVE_INFINITY, 1.0) }
    }

    @Test
    fun `solve should throw if c is NaN or Infinite`() {
        assertThrows<IllegalArgumentException> { quadraticSolver.solve(1.0, 1.0, Double.NaN) }
        assertThrows<IllegalArgumentException> { quadraticSolver.solve(1.0, 1.0, Double.POSITIVE_INFINITY) }
        assertThrows<IllegalArgumentException> { quadraticSolver.solve(1.0, 1.0, Double.NEGATIVE_INFINITY) }
    }
    
}
