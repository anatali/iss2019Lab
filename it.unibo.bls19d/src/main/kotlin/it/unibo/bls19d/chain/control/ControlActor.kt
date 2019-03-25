package it.unibo.bls19d.chain.control

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage

class ControlActor( name : String ) : ActorBasic(name){
    init{
        println(" ----------------------------------------- ControlActor $name STARTS")

    }
    override suspend fun actorBody(msg: ApplMessage){
        println( "ControlActor $name | RECEIVED $msg " )
        forward("on", "on", "led1")
    }
}