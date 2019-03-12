package it.unibo.blsFramework.appl;

import it.unibo.bls.interfaces.ILed;
import it.unibo.bls.interfaces.IObservable;
import it.unibo.bls.utils.Utils;
//import it.unibo.blsFramework.applLogic.BlsApplicationLogic;
import it.unibo.blsFramework.kotlin.applLogic.BlsApplicationLogic;
import it.unibo.blsFramework.concreteDevices.LedObserver;
import it.unibo.blsFramework.interfaces.*;
import it.unibo.blsFramework.listener.ButtonObserver;
import it.unibo.blsFramework.models.ButtonModel;
import it.unibo.blsFramework.models.LedModel;

public class BlsFramework implements IBlsFramework {
	private String cmdName;
	private ILedModel ledmodel;
	private IButtonModel buttonmodel;
	private IAppLogic applLogic;
	protected IApplListener buttonObserver;
	protected ILedObserver ledObserver;
	protected IObservable concreteButton = null;
	protected ILed concreteLed = null;

	//Factory method
	public static BlsFramework createTheSystem(String cmdName) {
		return new BlsFramework(cmdName);
	}

	public BlsFramework(String cmdName) {
		Utils.showSystemInfo();
		this.cmdName = cmdName;
		createLogicalComponents();
		configureSystemArchitecture();
	}

	protected void createLogicalComponents() {
		ledmodel = LedModel.createLed();
		buttonmodel = ButtonModel.createButton(cmdName);
		applLogic = new BlsApplicationLogic();
		buttonObserver = ButtonObserver.createButtonListener();
		ledObserver = LedObserver.create();
	}

	protected void configureSystemArchitecture() {
		buttonmodel.addObserver(buttonObserver);
		ledmodel.addObserver(ledObserver);
		setApplLogic(applLogic);
	}

	/*
	 * Setter methods
	 */
	@Override //IBlsFrameworkFsm
	public void setConcreteLed(ILed led) {
		concreteLed = led;
		ledObserver.setLed(led);
	}

	@Override //IBlsFrameworkFsm
	public void addConcreteLed(ILed led) {
		ILedObserver ledObs = LedObserver.create();
		concreteLed = led;
		ledObs.setLed(led);
		ledmodel.addObserver(ledObs);
	}

	@Override //IBlsFrameworkFsm
	public void addConcreteButton(IObservable button) {
		//concreteButton = button;            //for a single controller
		button.addObserver(buttonmodel);  //STARTS
	}

	public void setApplLogic(IAppLogic appLogic) {
		applLogic.execute("stop");            //to promote applicationLogic
		applLogic = appLogic;
		applLogic.setControlled(ledmodel);
		buttonObserver.setControl(applLogic);
	}

	/*
	 * Selectors
	 */
	@Override
	public ILedModel getLedModel() {
		return ledmodel;
	}

	@Override
	public IButtonModel getButtonModel() {
		return buttonmodel;
	}

	@Override
	public ILed getLedConcrete() {
		return concreteLed;
	}

	@Override
	public IObservable getButtonConcrete() {
		return concreteButton;
	}

	@Override
	public IAppLogic getApplLogic() {
		return applLogic;
	}

	@Override
	public IApplListener getButtonObserver() {
		return buttonObserver;
	}

}

