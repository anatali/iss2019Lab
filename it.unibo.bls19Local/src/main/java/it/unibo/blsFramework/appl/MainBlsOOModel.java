package it.unibo.blsFramework.appl;
 

import it.unibo.bls.devices.mock.ButtonMock;
import it.unibo.bls.interfaces.IApplListener;
import it.unibo.bls.interfaces.IControlLed;
import it.unibo.bls.interfaces.IObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.kotlin.applLogic.BlsApplicationLogic;
import it.unibo.bls.listener.ButtonObserver;
import it.unibo.bls.utils.Utils;
import it.unibo.blsFramework.devices.mock.LedMock;
import it.unibo.blsFramework.interfaces.ILedObservable;
import it.unibo.blsFramework.models.ButtonModel;
import it.unibo.blsFramework.models.LedModel;

public class MainBlsOOModel  {
private String cmdName;
private ILedObservable ledmodel;
private IObserver buttonmodel;
private IControlLed applLogic;
protected IApplListener buttonObserver;

protected IObservable concreteButton = null;
protected IObserver concreteLed = null;

//Factory method
	public static MainBlsOOModel createTheSystem( String cmdName ){
		return new MainBlsOOModel( cmdName );
	}

   	protected MainBlsOOModel( String cmdName ) {
		Utils.showSystemInfo();
 		this.cmdName = cmdName;
		createLogicalComponents();
		createConcreteComponents();
		configureSystemArchitecture();
	}


 	protected void createLogicalComponents(){
		ledmodel       = LedModel.createLed();
	 	applLogic      = new BlsApplicationLogic();
    	buttonmodel    = ButtonModel.createButton(cmdName);
		buttonObserver = ButtonObserver.createButtonListener( );
 	}

	protected void createConcreteComponents(){
		 concreteLed    = LedMock.createLed() ;
		 concreteButton = ButtonMock.createButton();
  	}

 	protected void configureSystemArchitecture(){
		 ledmodel.addObserver(concreteLed);
		 buttonObserver.setControl( applLogic );
		 applLogic.setControlled (ledmodel );
		 concreteButton.addObserver(buttonmodel);  //STARTS
	}
/*
 * Selectors	
 */
 	public ILedObservable getLedModel() {
 		return ledmodel;
 	}
 	public IObserver getButtonModel() {
 		return buttonmodel;
 	}
 /*
  * Work

 	protected void blink() {
  		ledmodel.turnOff();
		Utils.delay(1000);
		ledmodel.turnOn(); 		
		Utils.delay(1000);
		ledmodel.turnOff();
		Utils.delay(1000);
		ledmodel.turnOn(); 		
 	}
 	  */
public static void main(String[] args) {
   MainBlsOOModel.createTheSystem( "BLS-blink"  );
 }
}