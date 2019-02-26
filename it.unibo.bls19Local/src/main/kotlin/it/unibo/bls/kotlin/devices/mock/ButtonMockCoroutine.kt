package it.unibo.bls.kotlin.devices.mock

import it.unibo.bls.kotlin.interfaces.IButtonObservable
import it.unibo.bls.kotlin.interfaces.IObserver
import it.unibo.bls.kotlin.utils.Utils
import java.util.Observable
import java.util.Observer
import kotlinx.coroutines.*

class ButtonMockCoroutine : Observable(), IButtonObservable {
    private var buttonState = false

    override//from Observable
    fun addObserver(observer: Observer) {
        super.addObserver(observer)
        GlobalScope.launch {
            simulateUserInteraction()
        }
    }

    override//from IButtonObservable
    fun addObserver(obs: IObserver) {
        this.addObserver(obs as Observer)  //Type casting
    }

    /*
Autnnomous behavior
 */
    private fun simulateUserInteraction() {
        println("	ButtonMockCoroutine | simulateUserInteraction ...")
        for (i in 1..4) {
            buttonState = !buttonState
            println("	ButtonMockCoroutine | click num=$i")
            this.setChanged()
            this.notifyObservers("click")
            Utils.delay(1000)
        }
    }

    companion object {
        //Factory method
        fun createButton(): IButtonObservable {
            return ButtonMockCoroutine()
        }
    }
}
