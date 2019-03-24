package it.unibo.bls19d.chain.control

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage

class ControlActor( name : String ) : ActorBasic(name){
    override suspend fun actorBody(msg: ApplMessage){
        println( "ControlActor $name | RECEIVED $msg " )
    }
}