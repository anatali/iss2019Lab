package it.unibo.bls.kotlin.applLogic

import it.unibo.bls.interfaces.IControlLed
import it.unibo.bls.interfaces.ILed
import it.unibo.bls.utils.Utils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

open class BlsApplicationLogic : IControlLed {
    protected var led: ILed? = null
    protected var numCalls = 0
    protected var doBlink = false
    protected val channel = Channel<Boolean>()

    override fun setControlled(led: ILed) {
        this.led = led
        GlobalScope.launch{
            println("FOR COROUTINE | numOfThreads=${Thread.activeCount()} currentThread=${Thread.currentThread().name}")
            doBlinkTheLedWaiting()
        }
    }

     private suspend fun doBlinkTheLedWaiting() {
         while (true) {
             doBlink = channel.receive()        //See execute
             println("	COROUTINE | received=$doBlink")
             if (doBlink) doBlinkTheLed()
         }
    }

    private fun doBlinkTheLed() {
        GlobalScope.launch {
            while ( doBlink ) {
                switchTheLed()
                Utils.delay(250)
            }
            println("	BlsApplicationLogicKt coroutine | doBlinkTheLed ENDS ...")
        }
    }

    protected fun switchTheLed() {
        if (led == null) return        //defensive programming
        if ( led!!.getState() ) led!!.turnOff() else led!!.turnOn()
    }

    override//from
    fun execute(cmd: String) {
        println("	BlsApplicationLogicKt | execute cmd=$cmd  ")
        applLogic( )
      }
     open fun applLogic( ){
        numCalls++
        doBlink = numCalls % 2 != 0
        println("	BlsApplicationLogicKt | execute numCalls=$numCalls doBlink=$doBlink")
        GlobalScope.launch {
            channel.send(doBlink)
        }
    }
    //Useful for testing
     override fun getNumOfCalls(): Int {
        return numCalls
    }
}
