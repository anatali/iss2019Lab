package it.unibo.bls.kotlin.devices.mock

import it.unibo.bls.interfaces.IButtonObservable
import it.unibo.bls.interfaces.IObserver
import it.unibo.bls.utils.Utils

import java.util.Observable
import java.util.Observer

class ButtonMock : Observable(), IButtonObservable {
    private var buttonState = false
    override//from Observable
    fun addObserver(observer: Observer) {
        super.addObserver(observer)
        object : Thread() {
            override fun run() {
                simulateUserInteraction()
            }
        }.start()
    }

    override//from IButtonObservable
    fun addObserver(observer: IObserver) {
        this.addObserver(observer as Observer)  //Type casting
    }

    /*
Autnnomous behavior
 */
    private fun simulateUserInteraction() {
        println("	ButtonMock | simulateUserInteraction ...")
        for (i in 1..4) {
            buttonState = !buttonState
            println("	ButtonMock | click num=$i")
            this.setChanged()
            this.notifyObservers("click")
            Utils.delay(1000)
        }
    }

    companion object {
        //Factory method
        fun createButton(): IButtonObservable {
            return ButtonMock()
        }
    }
}
