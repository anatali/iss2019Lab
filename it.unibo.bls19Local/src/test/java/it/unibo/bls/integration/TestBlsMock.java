package it.unibo.bls.integration;

import static org.junit.Assert.assertTrue;
import it.unibo.bls.appl.MainBlsMockBase;
import it.unibo.bls.interfaces.IControlLed;
import it.unibo.bls.interfaces.IButtonListener;
import it.unibo.bls.interfaces.ILed;
import it.unibo.bls.utils.Utils;
import org.junit.Before;
import org.junit.Test;

public class TestBlsMock {
private MainBlsMockBase sys;

	@Before
	public void setUp(){
		sys = MainBlsMockBase.createTheSystem();
	}

	@Test
	public void testSystemLogic(){
 		ILed led                     = sys.getLed();
		IButtonListener listener     = sys.getButtonObserver();
		IControlLed control          = sys.getApplLogic();
		Utils.delay(4000);
 		assertTrue( listener.getNumOfClicks()== control.getNumOfCalls()  );
	}

}
