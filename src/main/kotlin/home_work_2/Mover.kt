package home_work_2

class Mover {

    fun move(movable: Movable) {
        val position = movable.getPosition()
            ?: throw IllegalStateException("Position is not readable")
        val velocity = movable.getVelocity()
            ?: throw IllegalStateException("Velocity is not readable")
        val newPosition = position + velocity
        try {
            movable.setPosition(newPosition)
        } catch (e: Exception) {
            throw IllegalStateException("Cannot set new position", e)
        }
    }

}
