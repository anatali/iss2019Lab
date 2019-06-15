package itunibo.robotMbot

object globalTimer{
    private var timeAtStart: Long = 0

    fun startTimer() {
        timeAtStart = System.currentTimeMillis()
    }

    fun stopTimer() : Int{
        val duration = (System.currentTimeMillis() - timeAtStart).toInt()
        println("REACTION TIME FOR OBSTACLE = $duration")
        return duration
    }
}