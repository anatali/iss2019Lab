package it.unibo.blsFramework.kotlin.fsm

import it.unibo.blsFramework.interfaces.ILedModel
import kotlinx.coroutines.channels.SendChannel

interface IAppLogicFsm {
    fun getNumOfCalls(): Int //useful for testing
    fun setControlled(obj: ILedModel)
    fun getStateMachine( ) : SendChannel<Messages>
}
