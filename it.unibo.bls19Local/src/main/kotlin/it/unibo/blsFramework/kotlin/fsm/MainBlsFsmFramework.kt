package it.unibo.blsFramework.kotlin.fsm

import it.unibo.bls.devices.gui.ButtonAsGui
import it.unibo.bls.devices.gui.LedAsGui
import it.unibo.bls.interfaces.ILed
import it.unibo.bls.interfaces.IObservable
import it.unibo.bls.utils.Utils
import it.unibo.blsFramework.concreteDevices.LedObserver
import it.unibo.blsFramework.interfaces.IButtonModel
import it.unibo.blsFramework.interfaces.ILedModel
import it.unibo.blsFramework.interfaces.ILedObserver
import it.unibo.blsFramework.models.ButtonModel
import it.unibo.blsFramework.models.LedModel



class MainBlsFsmFramework(
    val cmdName: String,
    val concreteButton: IObservable,
    val concreteLed: ILed
)  : IBlsFrameworkFsm {

    override val ledmodel: ILedModel             = LedModel.createLed()
    override val buttonmodel: IButtonModel       = ButtonModel.createButton("click")
    override val buttonObserver: ButtonObserver  = ButtonObserver.createButtonListener()
    override val ledObserver: ILedObserver       = LedObserver.create()

    private var applLogic: IAppLogicFsm   = BlsFsmAppl()
    set( value ) {
        forward( Messages.Stop, applLogic.getStateMachine() )     //to stop old ApplicationLogic
        value.setControlled(ledmodel)
        buttonObserver.setControl(value.getStateMachine())
    }

    init {
        println("MainBlsFsmFramework | init")
        configureSystemArchitecture()
    }


    protected fun configureSystemArchitecture() {
        buttonmodel.addObserver(buttonObserver)
        ledmodel.addObserver(ledObserver)
        concreteButton.addObserver(buttonmodel)
        ledObserver.setLed(concreteLed)
        applLogic.setControlled(ledmodel)
        buttonObserver.setControl( applLogic.getStateMachine() )
    }

    override    //IBlsFrameworkFsm
    fun addConcreteButton(button: IObservable) {
         button.addObserver(buttonmodel)  //STARTS
    }
    override    //IBlsFrameworkFsm
    fun addConcreteLed(led: ILed) {
        val ledObs = LedObserver.create()
        ledObs.setLed(led)
        ledmodel.addObserver(ledObs)
    }

    override
    fun changeApplLogic(appLogic: IAppLogicFsm) {
        applLogic = appLogic
    }

    companion object {
        //Factory method
        fun createTheSystem( logo: String, concreteButton: IObservable, concreteLed: ILed): MainBlsFsmFramework {
                return MainBlsFsmFramework(logo, concreteButton, concreteLed)
        }
/*
----------------------------------------
MAIN
----------------------------------------
 */
        @JvmStatic
        fun main( args: Array<String> ) {
            val blSystem = //MainBlsFsmFramework("clickMe")
                createTheSystem( "sys0",
                    ButtonAsGui.createButton("click"),
                    LedAsGui.createLed()
                )
             blSystem.addConcreteLed( LedAsGui.createLed() );

        Utils.delay(15000);
        System.out.println(" ================== CHANGE CONTROL ================= ");
        blSystem.changeApplLogic( AnotherBlsApplLogic() ) ;

    //blSystem.addConcreteLed(LedProxyArduino.create(DeviceConfig.serialPortNum, DeviceConfig.serialBaudrate));
            //blSystem.addConcreteButton(ButtonAsGui.createButton("LedControl"))

        }
    }//companion
}


