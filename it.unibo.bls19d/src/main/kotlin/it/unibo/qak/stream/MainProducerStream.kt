package it.unibo.qak.stream

import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.runBlocking

//MainProducerStream

fun main() = runBlocking{
    println("   MainProducerStream STARTS ")

    val prod   = ProducerStream("prod", this )
    val filter = Filter("filter",this )
    filter.setFilterFunction (  { v: Int -> v %2  != 0 } )
    val square = ConsumerSquare("square", this)
    val sink   = Sink("sink", this)

    prod.subscribe(filter).subscribe(square).subscribe(sink)

    val msgStart = MsgUtil.buildEvent("main", "start", "6"  )
    prod.actor.send(msgStart)
    println("   MainProducerStream ENDS ")
}