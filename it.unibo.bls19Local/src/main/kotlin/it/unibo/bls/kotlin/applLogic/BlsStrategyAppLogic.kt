package it.unibo.bls.kotlin.applLogic

import it.unibo.bls.interfaces.IControlLed
import it.unibo.bls.interfaces.ILed
import it.unibo.bls.utils.Utils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class BlsStrategyAppLogic : IControlLed {
    private var led: ILed? = null
    private var numOfCalls = 0
    private var doBlink = false
    private val channel = Channel<Boolean>()
    private val customApplLogic = ::builtInLogic //fun()->Unit;

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
             if (doBlink) doBlinkTheLed()
         }
     }
     private fun doBlinkTheLed() {
        GlobalScope.launch {
            while ( doBlink ) {
                switchTheLed()
                Utils.delay(250)
            }
            println("	BlsStrategyAppLogic coroutine | doBlinkTheLed ENDS ...")
        }
     }
     private fun switchTheLed() {
        if (led == null) return        //defensive programming
        if ( led!!.getState() ) led!!.turnOff() else led!!.turnOn()
     }

    override//from IControlLed
    fun execute(cmd: String) {
        customApplLogic()
        /*
        numOfCalls++
        doBlink = numOfCalls % 2 != 0
        println("	BlsStrategyAppLogic | execute numOfCalls=$numOfCalls doBlink=$doBlink")
        GlobalScope.launch {
            channel.send(doBlink)
        }
        */
    }

    fun builtInLogic(){
        numOfCalls++
        doBlink = numOfCalls % 2 != 0
        println("	BlsStrategyAppLogic | execute numOfCalls=$numOfCalls doBlink=$doBlink")
        GlobalScope.launch {
            channel.send(doBlink)
        }
    }
    fun exec( f:()->Unit ){ print(" exec| "); f() ; println(" |  done") }


    //Useful for testing
     override fun getNumOfCalls(): Int {
        return numOfCalls
    }
}
