package it.unibo.qak.prodConsKotlin

import it.unibo.kactor.sysUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

val simpleProducer : ReceiveChannel<Int> = GlobalScope.produce{
    for( i in 1..3 ){
        println( "simpleProducer produces $i in ${sysUtil.curThread()}")
        send( i )
    }
}

suspend fun consume(){
    val v = simpleProducer.receive()
    println( "consume receives ${v} in ${sysUtil.curThread()}" )
    simpleProducer.consumeEach {
        println( "consume receives $it in ${sysUtil.curThread()}" )
    }
}
fun main() = runBlocking{
    consume()
    println( "BYE")
}
