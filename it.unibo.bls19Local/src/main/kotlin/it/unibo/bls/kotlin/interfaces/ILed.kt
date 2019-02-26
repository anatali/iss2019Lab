package it.unibo.bls.kotlin.interfaces

interface ILed {
    fun getState(): Boolean
    fun turnOn()
    fun turnOff()
}
