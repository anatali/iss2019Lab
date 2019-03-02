package it.unibo.blsFramework.appl;
 
import it.unibo.bls.devices.DeviceConfig;
import it.unibo.bls.devices.arduino.ButtonProxyArduino;
import it.unibo.bls.devices.gui.ButtonAsGui;
import it.unibo.bls.devices.mock.ButtonMock;
import it.unibo.bls.interfaces.*;
import it.unibo.bls.kotlin.applLogic.BlsApplicationLogic;
import it.unibo.bls.listener.ButtonObserver;
import it.unibo.bls.utils.Utils;
import it.unibo.blsFramework.concreteDevices.arduino.LedProxy;
import it.unibo.blsFramework.concreteDevices.gui.LedGui;
import it.unibo.blsFramework.concreteDevices.mock.LedMock;
import it.unibo.blsFramework.interfaces.IBlsFramework;
import it.unibo.blsFramework.interfaces.IButtonModel;
import it.unibo.blsFramework.interfaces.ILedModel;
import it.unibo.blsFramework.models.ButtonModel;
import it.unibo.blsFramework.models.LedModel;

public class MainBlsOOModel implements IBlsFramework {
	private String cmdName;
	private ILedModel ledmodel;
	private IButtonModel buttonmodel;
	private IControlLed applLogic;
	protected IApplListener buttonObserver;

	protected IObservable concreteButton = null;
	protected IObserver concreteLed = null;

	//Factory method
	public static MainBlsOOModel createTheSystem(String cmdName) {
		return new MainBlsOOModel(cmdName);
	}

	protected MainBlsOOModel(String cmdName) {
		Utils.showSystemInfo();
		this.cmdName = cmdName;
		createLogicalComponents();
		//createConcreteComponents();
		configureSystemArchitecture();
	}

	protected void createLogicalComponents() {
		ledmodel = LedModel.createLed();
		applLogic = new BlsApplicationLogic();
		buttonmodel = ButtonModel.createButton(cmdName);
		buttonObserver = ButtonObserver.createButtonListener();
	}

	protected void createConcreteComponents() {
		concreteLed = LedMock.createLed();
		concreteButton = ButtonMock.createButton();
	}

	protected void configureSystemArchitecture() {
		applLogic.setControlled(ledmodel);
		buttonObserver.setControl(applLogic);
		buttonmodel.addObserver(buttonObserver);
	}

	public void setConcreteLed(IObserver led) {
		concreteLed = led;
		ledmodel.addObserver(concreteLed);
	}

	public void setConcreteButton(IObservable button) {
		concreteButton = button;
		concreteButton.addObserver(buttonmodel);  //STARTS
	}

	/*
	 * Selectors
	 */
	public ILedModel getLedModel() {
		return ledmodel;
	}

	public IButtonModel getButtonModel() {
		return buttonmodel;
	}

	public IObserver getLedConcrete() {
		return concreteLed;
	}

	public IObservable getButtonConcrete() {
		return concreteButton;
	}

	public static void main(String[] args) {
		IBlsFramework blSystem = MainBlsOOModel.createTheSystem("BLS-blink");
	/*
	//Inject the concrete MOCK devices
		blSystem.setConcreteLed( LedMock.createLed() );
		blSystem.setConcreteButton( ButtonMock.createButton() );

	//Inject the concrete GUI devices
		blSystem.setConcreteLed( LedGui.createLed() );
		blSystem.setConcreteButton( ButtonAsGui.createButton("ClickMe") );


	//Inject the concrete Led Arduino devices
		blSystem.setConcreteLed(LedProxy.createLed(DeviceConfig.serialPortNum, DeviceConfig.serialBaudrate));
		blSystem.setConcreteButton(ButtonAsGui.createButton("ClickMe"));

	//Inject the concrete Button Arduino devices
		blSystem.setConcreteLed( LedGui.createLed() );
		blSystem.setConcreteButton( new ButtonProxyArduino(DeviceConfig.serialPortNum, DeviceConfig.serialBaudrate));
	*/

	//CHANGE THE SYSTEM DYNAMICALLY BY ADDING TO A GUI LED AN ARDUINO LED
		blSystem.setConcreteLed( LedGui.createLed() );
		blSystem.setConcreteButton(ButtonAsGui.createButton("ClickMe"));
		Utils.delay(5000);
		blSystem.setConcreteLed(LedProxy.createLed(DeviceConfig.serialPortNum, DeviceConfig.serialBaudrate));
	}
}