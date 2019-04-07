package it.unibo.qak.producer

import it.unibo.kactor.*
import it.unibo.qak.prodCons.DataItem
import kotlinx.coroutines.delay

class Producer( name : String ) : ActorBasic( name ) {
var n = 1
    override suspend fun actorBody(msg: ApplMessage) {
        //println("   Producer $name |  receives msg= $msg ")
        when ( msg.msgId() ) {
            "start" -> {
                //println("   Producer $name |  receives msg= $msg ")
                //for (i in 1..2) {
                    val d = DataItem( "data${n++}")
                    //forward("data", "item$i", "consumer")
                    emit(d.id, d.item)
                //}
            }
            else -> println("   Producer $name |  msg= $msg ")
        }
    }
}
