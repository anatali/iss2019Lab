package it.unibo.bls.kotlin.applLogic

import it.unibo.bls.interfaces.IObservable
import it.unibo.bls.interfaces.IObserver
import it.unibo.blsFramework.interfaces.IAppLogic

interface IBlsFramework {
    fun setConcreteLed(led: IObserver)
    fun setConcreteButton(button: IObservable)
    fun setApplLogic(  arg : IAppLogic)
}
