package it.unibo.bls.components;

import static org.junit.Assert.*;
import it.unibo.bls.interfaces.IButtonListener;
import it.unibo.bls.interfaces.IButtonObservable;
import it.unibo.bls.interfaces.IControlLed;
import it.unibo.bls.listener.ButtonObserver;
import it.unibo.bls.utils.Utils;
import org.junit.Test;
import it.unibo.bls.applLogic.BlsApplicationLogic;
import it.unibo.bls.devices.mock.ButtonMock;

public class TestButton {
   	@Test
	public void testTheButtonMock(){
		IButtonObservable button  = ButtonMock.createButton();
		IControlLed applLogic        = new BlsApplicationLogic();
		IButtonListener buttonObs   = ButtonObserver.createButtonListener( );
		buttonObs.setControl( applLogic );
		button.addObserver( buttonObs );	//Starts
		Utils.delay(4000);				//Give the time to work
		System.out.println("NumOfClicks="+buttonObs.getNumOfClicks());
		assertTrue( buttonObs.getNumOfClicks() == applLogic.getNumOfCalls() );

 	}
}
