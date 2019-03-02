package it.unibo.bls.kotlin.applLogic

import it.unibo.bls.devices.gui.ButtonAsGui
import it.unibo.bls.interfaces.IApplListener
import it.unibo.bls.interfaces.IControlLed
import it.unibo.bls.interfaces.IObservable
import it.unibo.bls.interfaces.IObserver
import it.unibo.bls.listener.ButtonObserver
import it.unibo.bls.utils.Utils
import it.unibo.blsFramework.concreteDevices.gui.LedGui
//import it.unibo.bls.kotlin.applLogic.IBlsFramework
import it.unibo.blsFramework.interfaces.IButtonModel
import it.unibo.blsFramework.interfaces.ILedModel
import it.unibo.blsFramework.models.ButtonModel
import it.unibo.blsFramework.models.LedModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MyApplLogic : BlsApplicationLogic(){
    fun myAppLogic( arg : BlsApplicationLogic ) : Unit{
        numCalls++
        doBlink = numCalls % 2 != 0
        println("	myAppLogic | execute numOfCalls=$numCalls doBlink=$doBlink")
        GlobalScope.launch {
            channel.send(doBlink)
        }
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
    private var applLogic: IControlLed? = null
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
        ledModel      = LedModel.createLed()
        applLogic     = BlsApplicationLogic()
        buttonModel   = ButtonModel.createButton(cmdName)
        buttonObserver = ButtonObserver.createButtonListener()
    }

    protected fun configureSystemArchitecture() {
        applLogic!!.setControlled(ledModel)
        buttonObserver!!.setControl(applLogic)
        buttonModel!!.addObserver(buttonObserver)
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
    fun setApplLogic( f:(arg : BlsApplicationLogic )-> Unit  ){
        (applLogic as BlsApplicationLogic)!!.setApplLogic( f )
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
            blSystem.setApplLogic (
                {
                    it.numCalls++
                    it.doBlink = it.numCalls % 2 != 0
                    println("	myAppLogiccccccccccccccc | execute numOfCalls=${it.numCalls} doBlink=${it.doBlink}")
                    GlobalScope.launch {
                        it.channel.send(it.doBlink)
                    }
                }
             )
            blSystem.setConcreteLed(LedGui.createLed())
            blSystem.setConcreteButton(ButtonAsGui.createButton("ClickMe"))

        }
    }
}