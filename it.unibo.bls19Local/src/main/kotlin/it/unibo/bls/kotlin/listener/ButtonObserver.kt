package it.unibo.bls.kotlin.listener

import it.unibo.bls.kotlin.interfaces.IApplListener
import it.unibo.bls.kotlin.interfaces.IControl
import java.util.Observable

class ButtonObserver private constructor() : IApplListener {
    private var controller: IControl? = null
    private var count = 0
    override fun setControl(ctrl: IControl) {
        this.controller = ctrl
    }

    override fun getNumOfClicks(): Int {
        return count
    }

    override//from IObserver -> Observer
    fun update(source: Observable, state: Any) {
        count++
        //System.out.println("ButtonListener update | state=" + state + " controller=" + controller);
        controller!!.execute(state.toString())
    }

    companion object {
        //Factory method
        fun createButtonListener(): ButtonObserver {
            return ButtonObserver()
        }
    }
}
