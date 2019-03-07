package it.unibo.blsFramework.kotlin.fsm

import kotlinx.coroutines.channels.SendChannel
import java.util.Observable


class ButtonObserver private constructor() : IButtonListener {
    private var controller: SendChannel<Messages>? = null

    private var count = 0

    override
    fun setControl(ctrl: SendChannel<Messages>) {
        this.controller = ctrl
    }

    override
    fun getNumOfClicks(): Int {
        return count
    }

    override//from IButtonListener IObserver -> Observer
    fun update(source: Observable, state: Any) {
        count++
        //println("   ButtonObserver  | update state=" + state + " controller=" + controller);
        forward( Messages.Click, controller!! )
    }

    companion object {
        //Factory method
        fun createButtonListener(): ButtonObserver {
            return ButtonObserver()
        }
    }
}
