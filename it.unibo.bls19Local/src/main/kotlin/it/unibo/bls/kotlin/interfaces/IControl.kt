package it.unibo.bls.kotlin.interfaces

interface IControl {
    fun getNumOfCalls() : Int //useful for testing
    fun execute(cmd: String)
}
