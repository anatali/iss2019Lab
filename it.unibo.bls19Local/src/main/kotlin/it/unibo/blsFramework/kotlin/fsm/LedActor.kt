package it.unibo.blsFramework.kotlin.fsm

import it.unibo.bls.interfaces.ILed
import it.unibo.bls.utils.Utils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext

class LedActor(val name: String, val led : ILed){
      val dispatcher = newFixedThreadPoolContext(2, "mypool")
      var nextledActor : SendChannel<LedCtrlMsg>? = null
      var stopped    = true;

    init{

    }
    @kotlinx.coroutines.ObsoleteCoroutinesApi
    val ledactor = GlobalScope.actor<LedCtrlMsg>(dispatcher, 1 ) {
        //receive
        for( msg in channel ) {
            println("   ACTOR $name |  msg= $msg "  )
            ledCtrl( msg )
          }
    }

    suspend fun ledCtrl( cmd : LedCtrlMsg ){
        when( cmd ){
            LedCtrlMsg.START -> if( stopped ) {  doBlinkAndPropagate() }
            LedCtrlMsg.STOP  -> if( ! stopped ) {  stopped = true; nextledActor!!.send(LedCtrlMsg.STOP) }
            LedCtrlMsg.ON    -> {led.turnOn();   Utils.delay(500); nextledActor!!.send(LedCtrlMsg.ON)}
            LedCtrlMsg.OFF   -> {led.turnOff();  Utils.delay(500); nextledActor!!.send(LedCtrlMsg.OFF)}
       }
    }

    suspend private fun doBlinkAndPropagate(){
        led.turnOn()
        Utils.delay(250)
        nextledActor!!.send(LedCtrlMsg.START)
        led.turnOff()
    }
    /*
    private fun doBlinkTheLedChain() {
        GlobalScope.launch {
            //while ( doBlink ) {
                if ( led.getState() ) led.turnOff() else led.turnOn() //switch
                propagate( led.getState() )
                Utils.delay(250)
            //}
            println("	BlsApplicationLogicKt coroutine | doBlinkTheLed ENDS ...")
        }
    }

    suspend protected fun propagate( state : Boolean) {
        when( state ){
            true  -> nextledActor!!.send(LedCtrlMsg.ON)
            false -> nextledActor!!.send(LedCtrlMsg.OFF)
        }
     }
 */
    fun getActor() : SendChannel<LedCtrlMsg> {
        return ledactor
    }
    fun setNextLedActor( a : SendChannel<LedCtrlMsg> )  {
        nextledActor = a
    }

}