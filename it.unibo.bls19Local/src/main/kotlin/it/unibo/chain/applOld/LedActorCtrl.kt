package it.unibo.chain.applOld

import it.unibo.bls.interfaces.ILed
import it.unibo.bls.utils.Utils
import it.unibo.kactor.ApplMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.newFixedThreadPoolContext

class LedActorCtrl(val name: String, val led : ILed){
    private val dispatcher = newFixedThreadPoolContext(2, "mypool")
    private var nextledActor : SendChannel<ApplMessage>? = null
    private var stopped    = false
    private val dt         = 250
    private var count:Int  = 0

    private fun startMsg() : ApplMessage {
        count++
        return ApplMessage("msg( start, event, $name, none, start, $count )")
    }
    private fun stoptMsg() : ApplMessage {
        count++
        return ApplMessage("msg( stop, event, $name, none, stop, $count )")
    }
    init{
        println("   ACTOR $name |  INIT  ")
    }


    @kotlinx.coroutines.ObsoleteCoroutinesApi
    val ledactor = GlobalScope.actor<ApplMessage>(dispatcher, 3 ) {
        for( msg in channel ) {
            println("   ACTOR $name |  msg= $msg "  )
            if(! stopped ) ledCtrl( msg )
        }
    }

    suspend fun ledCtrl( cmd : ApplMessage){
        when( cmd.msgId() ){
            "start" ->  {  doBlinkAndPropagate() }
            "stop"  ->  {
                if( ! stopped ) {
                    led.turnOff()
                    stopped=true
                    if( nextledActor is SendChannel<ApplMessage>) nextledActor!!.send( stoptMsg() )}
                    //Utils.delay(5000)
                    //ledactor.close()
            }
            //LedCtrlMsg.ON    -> {led.turnOn();   Utils.delay(dt); nextledActor!!.send(startMsg())}
            //LedCtrlMsg.OFF   -> {led.turnOff();  Utils.delay(dt); nextledActor!!.send(startMsg())}
            else -> { println("   ACTOR $name |  msg= $cmd UNKNOWN ")}
       }
    }

    suspend private fun doBlinkAndPropagate(){
        led.turnOn()
        Utils.delay(dt)
        if( nextledActor is SendChannel<ApplMessage>) nextledActor!!.send( startMsg() )
        led.turnOff()
    }

    fun getActor() : SendChannel<ApplMessage> {
        return ledactor
    }
    fun setNextLedActor( a : SendChannel<ApplMessage> )  {
        println("   ACTOR $name |  SET NEXT ACTOR $a  ")
        nextledActor = a
    }

}