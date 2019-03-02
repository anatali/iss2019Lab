package it.unibo.bls.kotlin.applLogic

import experiment.useful
import it.unibo.bls.interfaces.IControlLed
import it.unibo.bls.interfaces.ILed
import it.unibo.bls.utils.Utils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

open class BlsApplicationLogic : IControlLed {
    protected var led: ILed? = null
    public var numCalls = 0
    public var doBlink = false
    public val channel = Channel<Boolean>()

    protected var currentApplLogic : (arg : BlsApplicationLogic)-> Unit = ::builtInLogic

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
            println("	BlsApplicationLogicKt coroutine | doBlinkTheLed ENDS ...")
        }
    }

    private fun switchTheLed() {
        if (led == null) return        //defensive programming
        if ( led!!.getState() ) led!!.turnOff() else led!!.turnOn()
    }

    override//from IControlLed
    fun execute(cmd: String) {
        currentApplLogic( this )
    }
    open fun builtInLogic(arg : BlsApplicationLogic){
        numCalls++
        doBlink = numCalls % 2 != 0
        println("	BlsStrategyAppLogic | execute numCalls=$numOfCalls doBlink=$doBlink")
        GlobalScope.launch {
            channel.send(doBlink)
        }
    }

    fun setApplLogic( f:(arg : BlsApplicationLogic)-> Unit ){
        currentApplLogic = f
    }

    //Useful for testing
     override fun getNumOfCalls(): Int {
        return numOfCalls
    }
}
