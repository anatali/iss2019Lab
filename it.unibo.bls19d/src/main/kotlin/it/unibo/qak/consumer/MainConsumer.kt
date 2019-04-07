package it.unibo.qak.consumer

import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    QakContext.createContexts(
            "localhost",this,
            "src/main/kotlin/it/unibo/qak/prodCons/sysDescr.pl",
            "src/main/kotlin/it/unibo/qak/prodCons/sysRules.pl"
    )

 //   val ctx = sysUtil.getContext("ctxConsumer")
 //   sysUtil.createActor(ctx!!, "consumerNew", "it.unibo.qak.consumer.Consumer")
}