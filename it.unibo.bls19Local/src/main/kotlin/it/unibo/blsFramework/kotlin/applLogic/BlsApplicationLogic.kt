package it.unibo.blsFramework.kotlin.applLogic

import it.unibo.bls.interfaces.ILed
import it.unibo.bls.utils.Utils
import it.unibo.blsFramework.interfaces.IAppLogic
import it.unibo.blsFramework.interfaces.ILedModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext

open class BlsApplicationLogic : IAppLogic {
    protected var led: ILedModel? = null
    protected var numCalls = 0
    protected var doBlink = false
    protected val channel = Channel<Boolean>()
    //protected var actorBlink : Job?  = null

    override fun setControlled(led: ILedModel) {
        this.led = led
        GlobalScope.launch{
            println("FOR COROUTINE | numOfThreads=${Thread.activeCount()} currentThread=${Thread.currentThread().name}")
            activateBlinkActor( led )
            doBlinkTheLedWaiting()
        }
    }
    private suspend fun doBlinkTheLedWaiting() {
        while (true) {
            doBlink = channel.receive()        //See execute
            println("	COROUTINE | received=$doBlink  led=$led")
            if (doBlink) actorBlink.send("")//doBlinkTheLed()
        }
    }

    private fun activateBlinkActor( led : ILedModel ){
        var dispatcher = newFixedThreadPoolContext(3, "mypool")
        actorBlink = GlobalScope.actor<String>(dispatcher, 1 ) {
            for( msg in channel ) {
                println("ACTOR buffered | ${this.channel.isFull}  msg= ${msg} in ${Thread.currentThread().name}")

            }
        }
    }


    private fun doBlinkTheLed() {
        GlobalScope.launch {
            while ( doBlink ) {
                switchTheLed()
                Utils.delay(250)
            }
            println("	BlsFrameworkApplicationLogicKt coroutine | doBlinkTheLed ENDS ...")
        }
    }

    protected fun switchTheLed() {
        if (led == null) return        //defensive programming
        if ( led!!.getState() ) led!!.turnOff() else led!!.turnOn()
    }

    override//from
    fun execute(cmd: String) {
        //println("	BlsFrameworkApplicationLogicKt | execute cmd=$cmd  ")
        if (cmd == "stop") {
            doBlink = false
            return
        }
        applLogic( )
      }
     open fun applLogic( ){
        numCalls++
        doBlink = numCalls % 2 != 0
        //println("	BlsFrameworkApplicationLogicKt | execute numCalls=$numCalls doBlink=$doBlink")
        GlobalScope.launch {
            channel.send(doBlink)
        }
    }
    //Useful for testing
     override fun getNumOfCalls(): Int {
        return numCalls
    }
}
