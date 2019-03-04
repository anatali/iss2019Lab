package it.unibo.blsFramework.kotlin.applLogic

import it.unibo.bls.utils.Utils
import it.unibo.blsFramework.interfaces.IAppLogic
import it.unibo.blsFramework.interfaces.ILedModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext

open class BlsApplicationLogic : IAppLogic {
    protected var led: ILedModel? = null
    protected var numCalls = 0
    protected var doBlink = false
    @kotlinx.coroutines.ObsoleteCoroutinesApi
    protected val dispatcher = newFixedThreadPoolContext(3, "mypool")
    //protected val jobBlink = Job();
    /*
    PROACTIVE PART
    The actor returns a send channel
     */
    @kotlinx.coroutines.ObsoleteCoroutinesApi
    val actorBlink = GlobalScope.actor<String>(dispatcher, 1 ) {
        for( msg in channel ) {
            println("   ACTOR actorBlink |  msg= $msg doBlink= $doBlink ")
            while ( doBlink ) {
                //switch the led
                if ( led!!.getState() ) led!!.turnOff() else led!!.turnOn()
                //WARNING: inactive for 250 msec
                Utils.delay(250)
            }
            println("   ACTOR actorBlink  | doBlinkTheLed ENDS ...")
        }
    }

    override fun setControlled(led: ILedModel) {
        this.led = led
    }
    //REACTIVE PART
    override fun execute(cmd: String) {
        //println("	BlsFrameworkApplicationLogicKt | execute cmd=$cmd  ")
        if (cmd == "stop") { doBlink = false
        }else applLogic( )
    }

    open  fun applLogic( ){  //open : can be overridden
        numCalls++
        doBlink = numCalls % 2 != 0     //if false actorBlink ends its loop
        println("	BlsFrameworkApplicationLogicKt | execute numCalls=$numCalls doBlink=$doBlink")
        if( doBlink )
        GlobalScope.launch {
            actorBlink.send("work") //REACTIVATES the actor
        }
    }
    //Useful for testing
     override fun getNumOfCalls(): Int {
        return numCalls
    }
}
