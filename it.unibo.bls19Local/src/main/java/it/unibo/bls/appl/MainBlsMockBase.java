package it.unibo.bls.appl;

import it.unibo.bls.devices.ButtonType;
import it.unibo.bls.devices.DeviceFactory;
import it.unibo.bls.devices.LedType;
import it.unibo.bls.interfaces.*;
import it.unibo.bls.applLogic.BlsApplicationLogic;
import it.unibo.bls.listener.ButtonObserver;


public class MainBlsMockBase  {
	protected IButtonObservable btn;
	protected ILed led;
	protected IApplListener buttonObserver;
	protected IControl applLogic;
	protected DeviceFactory devFactory = new DeviceFactory();
	protected LedType ledType ;
	protected ButtonType buttonType;

//Factory method
  	public static MainBlsMockBase createTheSystem(){
  		return new MainBlsMockBase();
 	}
//Not-visible Constructor
 	protected MainBlsMockBase( ) {
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
		((BlsApplicationLogic)applLogic).setTheLed(led);  //type casting
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
	public IApplListener getButtonObserver(){
		return buttonObserver;
	}
	public IControl getApplLogic(){
  		return applLogic;
	}
/*
MAIN
*/
	public static void main(String[] args) {
   		MainBlsMockBase sys = createTheSystem();
 	}
}