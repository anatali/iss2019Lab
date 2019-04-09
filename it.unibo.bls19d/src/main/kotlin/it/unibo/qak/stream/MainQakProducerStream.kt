package it.unibo.qak.stream

import it.unibo.kactor.QakContext
import kotlinx.coroutines.runBlocking

//MainQakProducerStream

fun main() = runBlocking{
    println("   MainQakProducerStream STARTS ")
    /*
    val prod  = ProducerStream("prod",  this)
    val cons1 = ConsumerSquare("consSquare", this)
    val cons2 = ConsumerShow("consShow", this)

    val msg = MsgUtil.buildEvent("main", "start", "start"  )
    prod.actor.send( msg )

    delay( 2000 )

    prod.subscribe(cons1)
    cons1.subscribe(cons2)
    */

    QakContext.createContexts(
        "localhost", this,
        "src/main/kotlin/it/unibo/qak/stream/sysDescr.pl",
        "src/main/kotlin/it/unibo/qak/stream/sysRules.pl"
    )

    val producer = QakContext.getActor("producer")
    val cons     = ConsumerShow("consShow", this)
    (producer as ObservableActor).subscribe(cons)

    //delay(2000)
}