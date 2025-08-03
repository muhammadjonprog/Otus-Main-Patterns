package org.bot.home_work_4.fuel_system

class FuelSystem(private var fuel: Int) {

    fun hasFuel(amount: Int): Boolean = fuel >= amount

    fun consume(amount: Int) {
        fuel -= amount
        println("Топливо расходовано: $amount, осталось: $fuel")
    }

    fun restore(amount: Int) {
        fuel += amount
        println("Топливо восстановлено: $amount, теперь: $fuel")
    }

    fun getCurrent(): Int = fuel
}
