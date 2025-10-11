// spaceship/operations/IMovable.kt
package home_work_6

interface IMovable {
    fun getPosition(): Vector
    fun setPosition(newValue: Vector)
    fun getVelocity(): Vector
    fun finish()
}

data class Vector(val x: Int, val y: Int)
