package home_work_2

class Rotator {

    fun rotate(rotatable: Rotatable, angleDelta: Double) {
        val currentAngle = rotatable.getDirectionAngle()
        rotatable.setDirectionAngle((currentAngle + angleDelta) % 360)
    }

}
