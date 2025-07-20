package org.bot.home_work_1

fun interface QuadraticSolver {

    fun solve(a: Double, b: Double, c: Double): Array<Double>

    class Base() : QuadraticSolver {
        override fun solve(a: Double, b: Double, c: Double): Array<Double> {
            if (listOf(a, b, c).any { it.isNaN() || it.isInfinite() }) {
                throw IllegalArgumentException("Coefficients must be finite numbers")
            }
            if (kotlin.math.abs(a) < 1e-9) {
                throw IllegalArgumentException("Coefficient a must not be zero")
            }

            val discriminant = b * b - 4 * a * c
            val epsilon = 1e-10

            return when {
                discriminant < -epsilon -> arrayOf()
                kotlin.math.abs(discriminant) <= epsilon -> arrayOf(-b / (2 * a))
                else -> {
                    val sqrtD = kotlin.math.sqrt(discriminant)
                    val x1 = (-b + sqrtD) / (2 * a)
                    val x2 = (-b - sqrtD) / (2 * a)
                    arrayOf(x1, x2)
                }
            }
        }
    }

}