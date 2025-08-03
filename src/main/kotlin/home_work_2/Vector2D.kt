package home_work_2

import kotlin.math.cos
import kotlin.math.sin

data class Vector2D(val x: Double, val y: Double) {

    operator fun plus(other: Vector2D): Vector2D = Vector2D(x + other.x, y + other.y)

    fun isZero(): Boolean = x == 0.0 && y == 0.0

    fun rotate(angleDegrees: Double): Vector2D {
        val radians = Math.toRadians(angleDegrees)
        val cos = cos(radians)
        val sin = sin(radians)
        val newX = x * cos - y * sin
        val newY = x * sin + y * cos
        return Vector2D(newX, newY)
    }

}
