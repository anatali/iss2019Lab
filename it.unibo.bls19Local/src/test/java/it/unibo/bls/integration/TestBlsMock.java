package it.unibo.bls.integration;

import static org.junit.Assert.assertTrue;
import it.unibo.bls.appl.MainBlsMockBase;
import it.unibo.bls.interfaces.IApplListener;
import it.unibo.bls.interfaces.IControl;
import it.unibo.bls.utils.Utils;
import org.junit.Before;
import org.junit.Test;
import it.unibo.bls.interfaces.ILed;
 
public class TestBlsMock {
private MainBlsMockBase sys;

	@Before
	public void setUp(){
		sys = MainBlsMockBase.createTheSystem();
	}

	@Test
	public void testSystemLogic(){
 		ILed led                     = sys.getLed();
		IApplListener listener       = sys.getButtonObserver();
		IControl control             = sys.getApplLogic();
		Utils.delay(4000);
 		assertTrue( listener.getNumOfClicks()== control.getNumOfCalls()  );

	}
	/*
	@Test
	public void testWrongCmd(){
  		ILed led                     = sys.getLed();
		IApplListener listener       = sys.getButtonObserver();
		assertTrue( listener.getNumOfClicks()==0 && ! led.getState());
		//Utils.delay(1500);
	}
	*/

	
}
