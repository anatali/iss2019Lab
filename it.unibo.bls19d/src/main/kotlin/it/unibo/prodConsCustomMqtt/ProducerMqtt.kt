package it.unibo.prodConsCustomMqtt

import it.unibo.kactor.*
import it.unibo.qak.prodCons.DataItem
import kotlinx.coroutines.CoroutineScope

class ProducerMqtt( name : String, scope: CoroutineScope) : ActorBasic( name, scope ) {
    var n = 1

    init{
        mqtt.connect(name,"tcp://localhost:1883")
    }

    override suspend fun actorBody(msg: ApplMessage) {
        println("   Producer $name |  receives msg= $msg ")
        when ( msg.msgId() ) {
            "local_start" -> {
                 //println("   Producer $name |  receives msg= $msg ")
                val d = DataItem( "data(${n})")
                    //forward("data", "item$i", "consumer")
                    //emit(d.id, d.item)
                val msg = MsgUtil.buildDispatch(name,"data",d.item, "consumers")
                mqtt.sendMsg(msg , "unibo/prodCons")
                n++
            }
            else -> println("   Producer $name |  msg= $msg ")
        }
    }
}
