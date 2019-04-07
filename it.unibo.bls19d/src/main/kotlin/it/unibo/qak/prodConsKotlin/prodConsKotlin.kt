package it.unibo.qak.prodConsKotlin

import it.unibo.kactor.sysUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

val context = newSingleThreadContext("myThread")

val producer: ReceiveChannel<Any> =
    GlobalScope.produce(context, 3){
        println( "producer sends 5   in ${sysUtil.curThread()}")
        send(5)
        println( "producer sends a   in ${sysUtil.curThread()}")
        send("a")
        println( "producer sends 100 in ${sysUtil.curThread()}")
        send(100)
    }

suspend fun consumer(){
    val v = producer.receive()
    println( "consumer receives $v in ${sysUtil.curThread()}")
    producer.consumeEach { println( "consumer receives $it in ${sysUtil.curThread()}") }
}


fun main() = runBlocking{
    consumer()
    //delay( 1000)
    println( "BYE")
}
