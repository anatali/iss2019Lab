package it.unibo.bls.kotlin.interfaces

interface IApplListener : IObserver {
    fun getNumOfClicks()  : Int
    fun setControl(ctrl: IControl)
}
