package it.unibo.qak.stream

import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

//MainProducerStream

fun main() = runBlocking{
    println("   MainProducerStream STARTS ")
    val prod  = ProducerStream("prod",  this)
    val cons1 = ConsumerSquare("consSquare", this)
    val cons2 = ConsumerShow("consShow", this)

    prod.subscribe(cons1)
    cons1.subscribe(cons2)

    val msg = MsgUtil.buildEvent("main", "start", "start"  )
    for( i in 1..5 ) {
        prod.actor.send(msg)
        delay(500)
    }


}