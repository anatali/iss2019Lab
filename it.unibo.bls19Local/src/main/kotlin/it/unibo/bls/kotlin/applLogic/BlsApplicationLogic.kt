package it.unibo.bls.kotlin.applLogic

import it.unibo.bls.kotlin.interfaces.*
import it.unibo.bls.kotlin.utils.Utils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BlsApplicationLogic : IControl {
    private var led: ILed? = null
    private var numOfCalls = 0

    fun setTheLed(led: ILed) {
        this.led = led
        doBlinkTheLed()
    }

    private fun doBlinkTheLed() {
        GlobalScope.launch {
            var numIter = 0
            println("	BlsApplicationLogic coroutine | simulateLedBlinking ...")
            while (true && numIter++ < 30) { //defensive programming
                    //System.out.println("	BlsApplicationLogic |  " + (count % 2) );
                if (numOfCalls % 2 != 0) { // odd
                        switchTheLed()
                }
                Utils.delay(250)
            }
        }
    }

    private fun switchTheLed() {
        if (led == null) return        //defensive programming
        if ( led!!.getState() ) led!!.turnOff() else led!!.turnOn()
    }

    override//from IControl
    fun execute(cmd: String) {
        numOfCalls++
        println("	BlsApplicationLogic | numOfCalls=$numOfCalls")
    }

    //Useful for testing
     override fun getNumOfCalls(): Int {
        return numOfCalls
    }
}
