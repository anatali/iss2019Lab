package it.unibo.blsFramework.kotlin.fsm

import it.unibo.bls.interfaces.ILed
import it.unibo.bls.interfaces.IObservable
import it.unibo.blsFramework.interfaces.*

internal interface IBlsFrameworkFsm {
    val ledmodel: ILedModel
    val buttonmodel: IButtonModel
    val buttonObserver: ButtonObserver
    val ledObserver: ILedObserver

    fun addConcreteButton(button: IObservable)
    fun addConcreteLed(led: ILed)
    fun changeApplLogic(appLogic: IAppLogicFsm)
}




