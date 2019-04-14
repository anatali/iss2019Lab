package it.unibo.qak.stream

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import kotlinx.coroutines.CoroutineScope

class Sink(name:String, scope: CoroutineScope) : ActorBasic( name, scope ){

    override suspend fun actorBody(msg : ApplMessage){
        println("   $name |  receives msg= $msg ")
        println()
     }


}