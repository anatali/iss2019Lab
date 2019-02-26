package it.unibo.bls.kotlin.appl

import it.unibo.bls.kotlin.applLogic.BlsApplicationLogic
import it.unibo.bls.kotlin.devices.ButtonType
import it.unibo.bls.kotlin.devices.DeviceFactory
import it.unibo.bls.kotlin.devices.LedType
import it.unibo.bls.kotlin.interfaces.IApplListener
import it.unibo.bls.kotlin.interfaces.IButtonObservable
import it.unibo.bls.kotlin.interfaces.IControl
import it.unibo.bls.kotlin.interfaces.ILed
import it.unibo.bls.kotlin.listener.ButtonObserver
import it.unibo.bls.kotlin.utils.Utils


class MainBlsMockBase//Not-visible Constructor
private constructor() {
    //Selectors (introduced for testing)
    var button: IButtonObservable? = null  //refactored
    var led: ILed? = null
    var actionListener: IApplListener? = null
    var applLogic: IControl? = null
        //get(){return field}
        //set(value){ field = value }
    val cmd = "click"
    private val devFactory = DeviceFactory()

    init {
        createTheComponents()
        connectTheComponents()
    }

    protected fun createTheComponents() {
        led = devFactory.createLed(LedType.LedMockObj)
        applLogic = BlsApplicationLogic()
        button = devFactory.createButton(ButtonType.ButtonMockObj)
        led!!.turnOff()
        actionListener = ButtonObserver.createButtonListener()
    }

    protected fun connectTheComponents() {
        println("KOTLIN VERSION connectTheComponents  ")
        actionListener!!.setControl(applLogic!!)
        (applLogic as BlsApplicationLogic).setTheLed(led!!)  //type casting
        button!!.addObserver(actionListener!!)    //starts the job
    }

    companion object {
        //Factory method
        fun createTheSystem(): MainBlsMockBase {
            return MainBlsMockBase()
        }

/*
MAIN
*/
        @JvmStatic
        fun main(args: Array<String>) {
            println("KOTLIN VERSION")
            createTheSystem()
            Utils.delay(5000)
        }
    }
}