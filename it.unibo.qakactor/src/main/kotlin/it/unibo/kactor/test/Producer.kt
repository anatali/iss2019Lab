package it.unibo.kactor.test

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage

class Producer( name : String ) : ActorBasic( name ) {
    override suspend fun actorBody(msg: ApplMessage) {
        println("   Producer $name |  receives msg= $msg ")
        for( i in 1..3 )
        forward("data", "item$i", "consumer")
    }
}
