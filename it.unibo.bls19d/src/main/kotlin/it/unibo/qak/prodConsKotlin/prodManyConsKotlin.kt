package it.unibo.qak.prodConsKotlin
//prodManyConsKotlin.kt
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

val aProducer : ReceiveChannel<Int> = GlobalScope.produce{
    for( i in 1..3 ){
        println( "aProducer produces $i in ${sysUtil.curThread()}")
        send( i )
    }
}

fun consumer1(scope: CoroutineScope){
    scope.launch{
        delay(100)
        val v = aProducer.receive()
        println( "consumer1 receives ${v} in ${sysUtil.curThread()}" )
     }
}
fun consumer2(scope: CoroutineScope){
    scope.launch{
        for( i in 1..2 ) {
            val v = aProducer.receive()
            println("consumer2 receives ${v} in ${sysUtil.curThread()}")
            delay(100)
        }
    }
}
fun main() = runBlocking{
    consumer1(this )
    consumer2(this)
    println( "BYE")
}
