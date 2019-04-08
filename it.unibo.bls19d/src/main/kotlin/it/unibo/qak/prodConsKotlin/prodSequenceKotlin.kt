package it.unibo.qak.prodConsKotlin
//prodSequenceKotlin

import it.unibo.kactor.sysUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce

val prodContext = newSingleThreadContext("myThread")

val fiboSeq = sequence{
    var a = 0
    var b = 1
    yield(1)           //first
    while (true) {
        yield(a + b)   //next
        val tmp = a + b
        a = b
        b = tmp
    }
}

val seqProd  = sequence{
    var v = 1
    for(i in 1..3){
        yield( v  )
        //println( "seqProd produced $v in ${sysUtil.curThread()}")
        v++
    }
    //println( "seqProd generateSequence  ")
    yieldAll( generateSequence(2) { it * 2  } )
}

suspend fun seqcons1( scope : CoroutineScope){
    println("seqcons1 STARTS")
    scope.launch {
        for( i in 0 .. 5 ) {
            val v = seqProd.elementAt(i)
            //val vlist = seqProd.take(3).toList()
            println("seqcons1 $i receives $v in ${sysUtil.curThread()}")
            delay(100) //release control
        }
    }
}

suspend fun seqcons2( scope : CoroutineScope ){
    println("seqcons2 STARTS")
    scope.launch {
        for( i in 1 .. 3 ) {
            val vlist = seqProd.take(i*3).filter { it % 2 == 0 }.toList()
            println("seqcons2 receives $vlist in ${sysUtil.curThread()}")
            delay(100) //release control
        }
    }
}


fun main() = runBlocking{
    seqcons1(this)
    seqcons2(this)
    println( "BYE")
}
