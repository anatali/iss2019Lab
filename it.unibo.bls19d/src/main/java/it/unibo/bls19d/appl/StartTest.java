package it.unibo.bls19d.appl;

import it.unibo.bls.devices.gui.ButtonAsGui;
import it.unibo.bls.devices.gui.LedAsGui;
import it.unibo.bls.utils.Utils;
import it.unibo.blsFramework.appl.MainBlsFramework;
import it.unibo.blsFramework.interfaces.IBlsFramework;
import it.unibo.blsFramework.kotlin.applLogic.AnotherApplLogic;

public class StartTest {
    public static void main(String[] args) {
        IBlsFramework blSystem = MainBlsFramework.createTheSystem("BLSFramework");
        blSystem.setConcreteLed( LedAsGui.createLed() );
        blSystem.addConcreteButton( ButtonAsGui.createButton("LedControl") );
        Utils.delay(5000);
        System.out.println(" ================== CHANGE CONTROL ================= ");
        blSystem.setApplLogic (  new AnotherApplLogic() );
    }
}
