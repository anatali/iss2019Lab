package it.unibo.bls19d.chain.control

import it.unibo.bls19d.chain.ChainMsg
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.delay

class ControlActor( name : String ) : ActorBasic(name){
    var goon = true
    val actors = sysUtil.ctxActorMap;

    init{
        println(" --- ControlActor $name CREATED")
    }
    /*
    Handles command form the user startChainBlink / stopChainBlink
     */
    override suspend fun actorBody(msg: ApplMessage){
        when( msg.msgId() ){
            ChainMsg.startChainBlink.name -> {
                if( ! goon ) return

                //val actors = sysUtil.ctxActorMap;
                actors.forEach{
                    println("ControlActor | ${it.key}")
                    forward("on", "on", "${it.key}")
                    delay(100)
                    forward("off", "off", "${it.key}")
                }
                /*
                forward("on", "on", "led1")
                delay(500)
                forward("off", "off", "led1")
                forward("on", "on", "led2")
                delay(500)
                forward("off", "off", "led2")
                forward("on", "on", "led3")
                delay(500)
                forward("off", "off", "led3")
                */
                autoMsg( ChainMsg.startChainBlink.name, ChainMsg.startChainBlink.name )
            }
            ChainMsg.stopChainBlink.name -> {
                goon = false
                //val actors = sysUtil.ctxActorMap;
                actors.forEach{
                    forward("off", "off", "${it.key}")
                }
                /*
                forward("off", "off", "led1")
                forward("off", "off", "led2")
                forward("off", "off", "led3")
                */
            }

        }
    }
}