package it.unibo.bls.components;

import static org.junit.Assert.*;
import it.unibo.bls.interfaces.IApplListener;
import it.unibo.bls.interfaces.IButtonObservable;
import it.unibo.bls.interfaces.IControl;
import it.unibo.bls.listener.ButtonObserver;
import it.unibo.bls.utils.Utils;
import org.junit.Test;
import it.unibo.bls.applLogic.BlsApplicationLogic;
import it.unibo.bls.devices.mock.ButtonMock;

public class TestButton {
   	@Test
	public void testTheButtonMock(){
		IButtonObservable button  = ButtonMock.createButton();
		IControl applLogic        = new BlsApplicationLogic();
		IApplListener buttonObs   = ButtonObserver.createButtonListener( );
		buttonObs.setControl( applLogic );
		button.addObserver( buttonObs );	//Starts
		Utils.delay(4000);				//Give the time to work
		System.out.println("NumOfClicks="+buttonObs.getNumOfClicks());
		assertTrue( buttonObs.getNumOfClicks() == applLogic.getNumOfCalls() );

 	}
}
