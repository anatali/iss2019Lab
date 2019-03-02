package it.unibo.bls.kotlin.applLogic

import it.unibo.bls.devices.gui.ButtonAsGui
import it.unibo.bls.interfaces.IObservable
import it.unibo.bls.interfaces.IObserver
import it.unibo.bls.utils.Utils
import it.unibo.blsFramework.concreteDevices.gui.LedGui
import it.unibo.blsFramework.interfaces.IAppLogic
import it.unibo.blsFramework.interfaces.IButtonModel
import it.unibo.blsFramework.interfaces.ILedModel
import it.unibo.blsFramework.models.ButtonModel
import it.unibo.blsFramework.models.LedModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MyApplLogic : BlsApplicationLogic(){
    override fun execute(  cmd: String  ) {
        myapplLogic(   )
    }
     fun myapplLogic(   ) {
        println("	myAppLogic | execute numOfCalls=$numCalls doBlink=$doBlink channel=$channel")
        //super.applLogic()
        switchTheLed()
        /*
        numCalls++
        doBlink = numCalls % 2 != 0
        println("	myAppLogic | execute numOfCalls=$numCalls doBlink=$doBlink  channel=$channel")
        GlobalScope.launch {
            channel.send(doBlink)
        }
        */
    }
}
class MainBlsFramework protected constructor(private val cmdName: String) : IBlsFramework {
    /*
	 * Selectors
	 */
    var ledModel: ILedModel? = null
        private set
    var buttonModel: IButtonModel? = null
        private set
    private var applLogic: IAppLogic? = null
    protected var buttonObserver: IApplListener? = null

    var buttonConcrete: IObservable? = null
        protected set
    var ledConcrete: IObserver? = null
        protected set

    init {
        Utils.showSystemInfo()
        createLogicalComponents()
        configureSystemArchitecture()
    }

    protected fun createLogicalComponents() {
        ledModel       = LedModel.createLed()
        applLogic      = BlsApplicationLogic()
        buttonModel    = ButtonModel.createButton(cmdName)
        buttonObserver = ButtonObserver.createButtonListener()
    }

    protected fun configureSystemArchitecture() {
        //applLogic!!.setControlled(ledModel!!)
        //buttonObserver!!.setControl(applLogic!!)
        buttonModel!!.addObserver(buttonObserver)
        setApplLogic(applLogic!!)
    }

    /*
	 * Setter methods
	 */
    override//IBlsFramework
    fun setConcreteLed(led: IObserver) {
        ledConcrete = led
        ledModel!!.addObserver(ledConcrete)
    }

    override//IBlsFramework
    fun setConcreteButton(button: IObservable) {
        buttonConcrete = button
        buttonConcrete!!.addObserver(buttonModel)  //STARTS
    }

    override//IBlsFramework
    fun setApplLogic( arg : IAppLogic){
        arg.setControlled(ledModel!!)
        buttonObserver!!.setControl(arg)
        //applLogic?.setApplLogic( arg )
    }

    companion object {
        //Factory method
        fun createTheSystem(cmdName: String): MainBlsFramework {
            return MainBlsFramework(cmdName)
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val blSystem = MainBlsFramework.createTheSystem("BLS-blink")
            /*
	//Inject the concrete MOCK devices
		blSystem.setConcreteLed( LedMock.createLed() );
		blSystem.setConcreteButton( ButtonMock.createButton() );

	//Inject the concrete Led Arduino devices
		blSystem.setConcreteLed(LedProxy.createLed(DeviceConfig.serialPortNum, DeviceConfig.serialBaudrate));
		blSystem.setConcreteButton(ButtonAsGui.createButton("ClickMe"));

	//Inject the concrete Button Arduino devices
		blSystem.setConcreteLed( LedGui.createLed() );
		blSystem.setConcreteButton( new ButtonProxyArduino(DeviceConfig.serialPortNum, DeviceConfig.serialBaudrate));


	//CHANGE THE SYSTEM DYNAMICALLY BY ADDING TO A GUI LED AN ARDUINO LED
		blSystem.setConcreteLed( LedGui.createLed() );
		blSystem.setConcreteButton(ButtonAsGui.createButton("ClickMe"));
		Utils.delay(5000);
		blSystem.setConcreteLed(LedProxy.createLed(DeviceConfig.serialPortNum, DeviceConfig.serialBaudrate));
	*/

            //Inject the concrete GUI devices
            blSystem.setApplLogic (  MyApplLogic() )
            blSystem.setConcreteLed(LedGui.createLed())
            blSystem.setConcreteButton(ButtonAsGui.createButton("ClickMe"))

        }
    }
}