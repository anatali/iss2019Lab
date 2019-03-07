package it.unibo.blsFramework.kotlin.fsm

import it.unibo.bls.interfaces.IObserver
import kotlinx.coroutines.channels.SendChannel

interface IButtonListener : IObserver {
    fun getNumOfClicks(): Int
    fun setControl(channel: SendChannel<Messages>)
}
