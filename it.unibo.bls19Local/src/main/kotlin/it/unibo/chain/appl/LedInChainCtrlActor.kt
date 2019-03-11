package it.unibo.chain.appl

import it.unibo.bls.interfaces.ILed
import it.unibo.bls.utils.Utils
import it.unibo.kactor.ApplMessage
import kotlinx.coroutines.channels.SendChannel
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.ActorBasic

class LedInChainCtrlActor(name: String, val led : ILed) : ActorBasic( name ){

    private var nextledActor : SendChannel<ApplMessage>? = null
    private var stopped    = false
    private val dt         = 250
    private var count:Int  = 0

    init{
        println("   LedInChainCtrlActor $name |  INIT  ")
    }

    override
    suspend fun actorBody( cmd : ApplMessage){
        if(! stopped )
        when( cmd.msgId() ){
            "start" ->  doBlinkAndPropagate()
            "stop"  ->  {
                if( ! stopped ) {
                    led.turnOff()
                    stopped=true
                    if( nextledActor is SendChannel<ApplMessage>) nextledActor!!.send( MsgUtil.stoptMsg() )}
                     //ledactor.close()
            }
            else -> { println("   LedInChainCtrlActor $name |  msg= $cmd UNKNOWN ")}
       }
    }

    suspend private fun doBlinkAndPropagate(){
        led.turnOn()
        Utils.delay(dt)
        if( nextledActor is SendChannel<ApplMessage>) nextledActor!!.send( MsgUtil.startMsg() )
        led.turnOff()
    }

     fun setNextLedActor( a : SendChannel<ApplMessage> )  {
        println("   LedCtrlActorname |  SET NEXT ACTOR $a  ")
        nextledActor = a
    }

}