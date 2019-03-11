package it.unibo.chain.model

import it.unibo.bls.interfaces.ILed
import it.unibo.blsFramework.interfaces.ILedObserver
import java.util.Observable

class LedInChainObs (val name : String) : ILedObserver {
    private var led: ILed? = null

    override//ILedObserver - IObserver
    fun update(o: Observable, arg: Any) {
        val newState = arg.toString() == "true"
        //println("LedInChainObs $name | update $newState  ")
        if (led != null && newState) led!!.turnOn() else led!!.turnOff()
    }

    override//ILedObserver
    fun setLed(led: ILed) {
        this.led = led
    }
}
