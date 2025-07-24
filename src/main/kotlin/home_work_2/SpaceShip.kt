package home_work_2

class SpaceShip(
    private var position: Vector2D,
    private var velocity: Vector2D,
    private var angle: Double
) : Movable, Rotatable {

    override fun getPosition(): Vector2D = position

    override fun getVelocity(): Vector2D = velocity

    override fun setPosition(newPosition: Vector2D) {
        this.position = newPosition
    }

    override fun getDirectionAngle(): Double = angle

    override fun setDirectionAngle(angle: Double) {
        this.angle = angle
    }

}
