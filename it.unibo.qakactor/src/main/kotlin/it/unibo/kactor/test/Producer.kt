package it.unibo.kactor.test

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.ApplMessageType

class Producer( name : String ) : ActorBasic( name ) {
    override suspend fun actorBody(msg: ApplMessage) {
        when (msg.msgId()) {
            "start" -> {
                println("   Producer $name |  receives msg= $msg ")
                for (i in 1..2)
                //forward("data", "item$i", "consumer")
                    emit("data", "item$i")
            }
            else -> println("   Producer $name |  msg= $msg ")
        }
    }
}
