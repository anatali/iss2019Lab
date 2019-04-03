package it.unibo.qak.consumer

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage


class Consumer( name : String ) : ActorBasic( name ) {

    override suspend fun actorBody(msg: ApplMessage) {
        println("   Consumer $name |  receives msg= $msg ")
    }
}
