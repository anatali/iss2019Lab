package it.unibo.qak.producer

import it.unibo.kactor.*
import it.unibo.qak.consumer.Consumer
import kotlinx.coroutines.runBlocking

fun main()= runBlocking {
    QakContext.createContexts(
        "localhost", this,
        "src/main/kotlin/it/unibo/qak/producer/sysDescr.pl",
        "sysRules.pl"
    )

    val consumer1 = QakContext.getActor("consumer1")
    val consumer2 = QakContext.getActor("consumer2")
    val logger    = QakContext.getActor("logger")

    consumer1!!.subscribe( logger!! )
    consumer2!!.subscribe( logger!! )
    println("END")

 //    val ctx = sysUtil.getContext("ctxConsumer")
//    sysUtil.createActor(ctx!!, "consumerNew", "it.unibo.qak.consumer.Consumer")

}




