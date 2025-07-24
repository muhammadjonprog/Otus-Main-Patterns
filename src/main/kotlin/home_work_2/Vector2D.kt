package home_work_2

data class Vector2D(val x: Double, val y: Double) {

    operator fun plus(other: Vector2D): Vector2D = Vector2D(x + other.x, y + other.y)

}
