package it.unibo.bls.integration;

import it.unibo.bls.devices.gui.ButtonAsGui;
import it.unibo.bls.devices.gui.LedAsGui;
import it.unibo.bls.utils.Utils;
import it.unibo.blsFramework.appl.MainBlsFramework;
import it.unibo.blsFramework.interfaces.IAppLogic;
import it.unibo.blsFramework.interfaces.IApplListener;
import it.unibo.blsFramework.interfaces.IBlsFramework;
import it.unibo.blsFramework.interfaces.ILedModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestBlsFramework {
private IBlsFramework sys;

	@Before
	public void setUp(){
		sys = MainBlsFramework.createTheSystem("BLSFramework");
		sys.setConcreteLed( LedAsGui.createLed() );
		sys.addConcreteButton( ButtonAsGui.createButton("Click") );
	}

	@Test
	public void testSystemLogic(){
 		ILedModel led              = sys.getLedModel();
		IApplListener buttonObs    = sys.getButtonObserver();
		IAppLogic appLogic         = sys.getApplLogic();
		Utils.delay(4000);
 		assertTrue( appLogic.getNumOfCalls()== appLogic.getNumOfCalls()  );
	}

}
