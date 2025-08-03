package org.bot.home_work_4.command

import home_work_2.SpaceShip
import org.bot.home_work_3.Command

class RotateCommandWithVelocity(
    private val spaceship: SpaceShip,
    private val angleDelta: Double
) : Command {

    override fun execute() {
        val newAngle = (spaceship.getDirectionAngle() + angleDelta) % 360
        spaceship.setDirectionAngle(newAngle)

        val velocity = spaceship.getVelocity()
        if (!velocity.isZero()) {
            val rotated = velocity.rotate(angleDelta)
            spaceship.setVelocity(rotated)
        }
    }

}