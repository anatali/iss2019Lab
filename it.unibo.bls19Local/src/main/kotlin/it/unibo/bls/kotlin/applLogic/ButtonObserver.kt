package it.unibo.bls.kotlin.applLogic
import it.unibo.blsFramework.interfaces.IAppLogic
import java.util.Observable

class ButtonObserver private constructor() : IApplListener {
    private var controller: IAppLogic? = null
    private var count = 0

    override fun setControl(ctrl: IAppLogic) {
        this.controller = ctrl
    }

    override fun getNumOfClicks(): Int {
        return count
    }

    override//from IApplListener IObserver -> Observer
    fun update(source: Observable, state: Any) {
        count++
        //System.out.println("ButtonListener update | state=" + state + " controller=" + controller);
        controller!!.execute(state.toString())
    }

    companion object {
        //Factory method
        fun createButtonListener(): IApplListener {
            return ButtonObserver()
        }
    }
}
