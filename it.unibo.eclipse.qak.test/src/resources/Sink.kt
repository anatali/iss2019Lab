package it.unibo.robots19.basic

import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope

class Sink(name:String, scope: CoroutineScope) : ActorBasic( name, scope ){
    override suspend fun actorBody(msg : ApplMessage){
        println("   $name |  receives msg= $msg ")
        //println()
     }
}