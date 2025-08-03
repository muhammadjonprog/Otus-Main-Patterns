package org.bot.home_work_4.command

import home_work_2.Rotator
import home_work_2.SpaceShip
import home_work_2.Vector2D
import org.bot.home_work_3.Command

class RotateCommand(private val spaceShip: SpaceShip, private val angleDelta: Double) : Command {

    override fun execute() {
        val ship = SpaceShip(Vector2D(0.0, 0.0), Vector2D(0.0, 0.0), 90.0)
        Rotator().rotate(spaceShip, angleDelta)
    }

}