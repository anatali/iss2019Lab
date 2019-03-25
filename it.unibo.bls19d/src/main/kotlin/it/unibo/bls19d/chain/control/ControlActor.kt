package it.unibo.bls19d.chain.control

import it.unibo.bls19d.chain.ChainMsg
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import kotlinx.coroutines.delay

class ControlActor( name : String ) : ActorBasic(name){
    var goon = true
    init{
        println(" ----------------------------------------- ControlActor $name STARTS")

    }

    /*
    Handles command form the user startChainBlink / stopChainBlink
     */
    override suspend fun actorBody(msg: ApplMessage){
        /*
        println( "ControlActor $name | RECEIVED $msg " )
        forward("on", "on", "led1")
        delay(500)
        forward("on", "on", "led2")
        delay(500)
        forward("on", "on", "led3")
    */
        when( msg.msgId() ){
            ChainMsg.startChainBlink.name -> {
                if( ! goon ) return
                forward("on", "on", "led1")
                delay(500)
                forward("off", "off", "led1")
                forward("on", "on", "led2")
                delay(500)
                forward("off", "off", "led2")
                forward("on", "on", "led3")
                delay(500)
                forward("off", "off", "led3")
                autoMsg( ChainMsg.startChainBlink.name, ChainMsg.startChainBlink.name )
            }
            ChainMsg.stopChainBlink.name -> {
                goon = false
                forward("off", "off", "led1")
                forward("off", "off", "led2")
                forward("off", "off", "led3")
            }

        }
    }
}