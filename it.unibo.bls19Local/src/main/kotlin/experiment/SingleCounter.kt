package experiment

object SingleCounter {
    private var counter = 0
    fun value(): Int { return counter }
    fun inc() { counter++ }
    fun dec() { counter-- }
    fun reset() { counter = 0 }
}

fun p2( c:SingleCounter ) : Int { return c.value()*c.value() }

fun main(){
    val c = SingleCounter
    val d = SingleCounter
    for( i in 1..3 ) c.inc()
    val v = p2( SingleCounter )
    println("c=${c.value()} d=${d.value()}" +
        " obj=${SingleCounter.value()} v=$v")
    SingleCounter.reset()
    println("c=${c.value()} d=${d.value()}" +
        " obj=${SingleCounter.value()}")
}