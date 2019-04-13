package it.unibo.qak.producer

import it.unibo.kactor.*
import kotlinx.coroutines.runBlocking

fun main()= runBlocking {
    QakContext.createContexts(
        "localhost", this,
        "src/main/kotlin/it/unibo/qak/prodCons/sysDescr.pl",
        "src/main/kotlin/it/unibo/qak/prodCons/sysRules.pl"
    )

    //delay(3000)
    //The producer will exist only after the activation of the consumer
   // val producer = QakContext.getActor("producer")
   // MsgUtil.sendMsg("start", "start", producer!!)

//    val ctx = sysUtil.getContext("ctxConsumer")
//    sysUtil.createActor(ctx!!, "consumerNew", "it.unibo.qak.consumer.Consumer")

}




