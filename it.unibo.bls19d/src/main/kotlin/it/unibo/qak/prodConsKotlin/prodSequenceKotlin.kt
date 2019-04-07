package it.unibo.qak.prodConsKotlin
//prodSequenceKotlin

import it.unibo.kactor.sysUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce

val prodContext = newSingleThreadContext("myThread")

val seqProd  = sequence{
    var v = 1
    while(true){
        println( "seqProd produces $v in ${sysUtil.curThread()}")
        yield( v++ )
    }
}

suspend fun seqcons1( scope : CoroutineScope){
    println("seqcons1 STARTS")
    scope.launch {
        val vlist = seqProd.take(3).toList()
        println("seqcons1 receives $vlist in ${sysUtil.curThread()}")
        delay(2000)
    }
}

suspend fun seqcons2( scope : CoroutineScope ){
    println("seqcons2 STARTS")
    scope.launch {
        val vlist = seqProd.take(7).filter { it % 2 != 0 }.toList()
        println("seqcons2 receives $vlist in ${sysUtil.curThread()}")
    }
}


fun main() = runBlocking{
    seqcons1(this)
    seqcons2(this)
    //delay( 1000)
    println( "BYE")
}
