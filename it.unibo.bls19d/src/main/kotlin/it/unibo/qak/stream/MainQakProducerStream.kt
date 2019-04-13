package it.unibo.qak.stream

import it.unibo.kactor.QakContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//MainQakProducerStream

fun main() = runBlocking{
    println("   MainQakProducerStream STARTS ")

    QakContext.createContexts(
        "localhost", this,
        "src/main/kotlin/it/unibo/qak/stream/sysDescr.pl",
        "sysRules.pl"
    )


    val cons1    = QakContext.getActor("consumer")
    val cons2    = ConsumerShow("consShow", this)

    val c1 =  cons1!! as ObservableActor
    c1.subscribe(cons2)

    println("MainQakProducerStream BYE")
}