package it.unibo.qak.stream

import it.unibo.kactor.QakContext
import kotlinx.coroutines.runBlocking

//MainQakProducerStream

fun main() = runBlocking{
    println("   MainQakProducerStream STARTS ")

    QakContext.createContexts(
        "localhost", this,
        "src/main/kotlin/it/unibo/qak/stream/sysDescr.pl",
        "sysRules.pl"
    )

    val square  = QakContext.getActor("consumer")
    val filter = Filter("filter",this )
    filter.setFilterFunction (  { v: Int -> v %2  != 0 } )
    val sink    = Sink("sink", this)
    square!!.subscribe(filter).subscribe(sink)

    println("MainQakProducerStream BYE")
}