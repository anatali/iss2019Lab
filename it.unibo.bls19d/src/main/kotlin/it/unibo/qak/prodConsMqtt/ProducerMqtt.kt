package it.unibo.qak.prodConsMqtt

import it.unibo.kactor.*
import it.unibo.qak.prodCons.DataItem
import kotlinx.coroutines.CoroutineScope

class ProducerMqtt( name : String, scope: CoroutineScope) : ActorBasic( name, scope ) {
    var n = 1

    override suspend fun actorBody(msg: ApplMessage) {
        println("   Producer $name |  receives msg= $msg ")
        when ( msg.msgId() ) {
            "local_start" -> {
                 //println("   Producer $name |  receives msg= $msg ")
                val d = DataItem( "data(${n})")
                emit(d.id, d.item)
                n++
            }
            else -> println("   Producer $name |  msg= $msg ")
        }
    }
}
