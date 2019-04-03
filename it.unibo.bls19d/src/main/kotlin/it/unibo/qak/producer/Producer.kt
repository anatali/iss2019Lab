package it.unibo.qak.producer

import it.unibo.kactor.*
import kotlinx.coroutines.delay

class Producer( name : String ) : ActorBasic( name ) {
    init{
         println("   Producer $name |  init: EXISTS NOW ")
    }

    override suspend fun actorBody(msg: ApplMessage) {
        println("   Producer $name |  receives msg= $msg ")
        for( i in 1..3 ) {
            delay( 500 )
            println("   Producer $name |  sends item$i ")
            forward("data", "item$i", "consumer")
        }
    }
}
