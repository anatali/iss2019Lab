package it.unibo.bls.integration;

import it.unibo.bls.devices.gui.ButtonAsGui;
import it.unibo.bls.devices.gui.LedAsGui;
import it.unibo.bls.utils.Utils;
import it.unibo.blsFramework.appl.MainBlsFramework;
import it.unibo.blsFramework.interfaces.IAppLogic;
import it.unibo.blsFramework.interfaces.IApplListener;
import it.unibo.blsFramework.interfaces.IBlsFramework;
import it.unibo.blsFramework.interfaces.ILedModel;
import it.unibo.blsFramework.kotlin.applLogic.AnotherApplLogic;
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
		sys.setApplLogic (  new AnotherApplLogic() );
	}

	@Test
	public void testSystemLogic(){
 		ILedModel led              = sys.getLedModel();
		IApplListener buttonObs    = sys.getButtonObserver();
		IAppLogic appLogic         = sys.getApplLogic();
		for( int i=1; i<= 5; i++){
			buttonObs.update(null,"click");
			Utils.delay(250);
			}
 		assertTrue( buttonObs.getNumOfClicks()== 5 && appLogic.getNumOfCalls() == 5  );
	}

}
