package it.unibo.blsFramework.kotlin.fsm

import it.unibo.bls.interfaces.ILed
import it.unibo.blsFramework.interfaces.ILedObserver
import java.util.Observable

class LedObserverAdapter : ILedObserver {
    private var led: ILed? = null

    override//ILedObserver - IObserver
    fun update(o: Observable, arg: Any) {
        val newState = arg.toString() == "true"
        if (led != null && newState) led!!.turnOff() else led!!.turnOn()
    }

    override//ILedObserver
    fun setLed(led: ILed) {
        this.led = led
    }
}
