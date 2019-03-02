package it.unibo.bls.kotlin.applLogic

import it.unibo.bls.interfaces.IObserver
import it.unibo.blsFramework.interfaces.IAppLogic

interface IApplListener : IObserver {
    fun getNumOfClicks() : Int
    fun setControl(ctrl: IAppLogic)
}
