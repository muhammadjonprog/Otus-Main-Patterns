package org.bot.home_work_4.command

import home_work_2.Mover
import home_work_2.SpaceShip
import home_work_2.Vector2D
import org.bot.home_work_3.Command

class MoveCommand(private val spaceShip: SpaceShip) : Command {

    override fun execute() {
        Mover().move(spaceShip)
    }

}