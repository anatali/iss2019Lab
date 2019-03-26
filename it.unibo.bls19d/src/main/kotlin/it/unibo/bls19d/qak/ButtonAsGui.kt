package it.unibo.bls19d.qak

import it.unibo.bls.devices.gui.ButtonBasic
import it.unibo.bls.interfaces.IButtonObservable
import it.unibo.bls.interfaces.IObserver
import it.unibo.bls.utils.Utils

import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.Observable

/*
Java does not support multiple inheritance among classes.
 */
class ButtonAsGui : Observable(), IButtonObservable, ActionListener {
    private val frame: Frame? = null
    private val cmd: String? = null
    override//from Observable and IButtonObservable
    fun addObserver(observer: IObserver) {
        super.addObserver(observer)
    }

    override//from ActionListener
    fun actionPerformed(e: ActionEvent) {
        //System.out.println("	ButtonAsGui | actionPerformed ..." + e.getActionCommand() );
        this.setChanged()
        this.notifyObservers(e.actionCommand)
    }

    companion object {
        //Factory method
        fun createButton(cmd: String): it.unibo.bls.devices.gui.ButtonAsGui {
            val button = it.unibo.bls.devices.gui.ButtonAsGui()
            val bb = ButtonBasic(Utils.initFrame(200, 200), cmd, button)    //button is the listener
            return button
        }
    }
}
