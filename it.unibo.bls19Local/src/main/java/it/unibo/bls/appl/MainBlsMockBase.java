package it.unibo.bls.appl;

import it.unibo.bls.devices.ButtonType;
import it.unibo.bls.devices.DeviceFactory;
import it.unibo.bls.devices.LedType;
import it.unibo.bls.interfaces.*;
import it.unibo.bls.applLogic.BlsApplicationLogic;			//JAVA IMPL
//import it.unibo.bls.kotlin.applLogic.BlsApplicationLogic;	//KOTLIN IMPL
import it.unibo.bls.listener.ButtonObserver;
import it.unibo.bls.utils.Utils;
import it.unibo.bls.interfaces.IButtonListener;


public class MainBlsMockBase  {
	protected IButtonObservable btn;
	protected ILed led;
	protected IButtonListener buttonObserver;
	protected IControlLed applLogic;
	protected DeviceFactory devFactory = new DeviceFactory();
	protected LedType ledType ;
	protected ButtonType buttonType;

//Factory method
  	public static MainBlsMockBase createTheSystem(){
  		return new MainBlsMockBase();
 	}
//Not-visible Constructor
 	protected MainBlsMockBase( ) {
		Utils.showSystemInfo();
		setDeviceTypes();
		createTheComponents();
		connectTheComponents();
		startTheSystem();
 	}
 	protected void setDeviceTypes(){
		ledType    = LedType.LedMockObj;
		buttonType = ButtonType.ButtonMockObj;
	}
 	protected void createTheComponents(){
  		led       = devFactory.createLed( ledType );
		applLogic = new BlsApplicationLogic();
 		btn       = devFactory.createButton( buttonType );
 		led.turnOff();
		buttonObserver = ButtonObserver.createButtonListener( );
	}
	protected void connectTheComponents(){
		buttonObserver.setControl( applLogic );
		applLogic.setControlled(led);
	}

	protected void startTheSystem(){
  		btn.addObserver( buttonObserver );	//starts the job
	}

	//Selectors (introduced for testing)
 	public IButtonObservable getButton(){
		return btn;
	}
	public ILed getLed(){
		return led;
	}
	public IButtonListener getButtonObserver(){
		return buttonObserver;
	}
	public IControlLed getApplLogic(){
  		return applLogic;
	}
/*
MAIN
*/
	public static void main(String[] args) {
   		MainBlsMockBase sys = createTheSystem();
 	}
}