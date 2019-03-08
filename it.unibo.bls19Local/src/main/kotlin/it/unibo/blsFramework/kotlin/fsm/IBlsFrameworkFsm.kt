package it.unibo.blsFramework.kotlin.fsm

import it.unibo.bls.interfaces.ILed
import it.unibo.bls.interfaces.IObservable
import it.unibo.blsFramework.interfaces.*
import kotlinx.coroutines.channels.SendChannel

internal interface IBlsFrameworkFsm {
    val ledmodel: ILedModel
    val buttonmodel: IButtonModel
    val buttonObserver: ButtonObserver
    val ledObserver: ILedObserver
    //val ledObserver: SendChannel<LedCtrlMsg>

    fun addConcreteButton(button: IObservable)
    fun addConcreteLed(led: ILed)
    fun changeApplLogic(appLogic: IAppLogicFsm)
}




