package it.unibo.bls.kotlin.devices.mock

import it.unibo.bls.kotlin.interfaces.ILed

class LedMock : ILed {
    private var state = false
    override fun turnOn() {
        state = true
        showState()
    }

    override fun turnOff() {
        state = false
        showState()
    }

    override fun getState(): Boolean {
        return state
    }

    private fun showState() {
        println("Led state=$state")
    }

    companion object {
        //Factory method
        fun createLed(): ILed {
            return LedMock()
        }
    }
}
