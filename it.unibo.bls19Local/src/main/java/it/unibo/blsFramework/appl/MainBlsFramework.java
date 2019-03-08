package it.unibo.blsFramework.appl;

import it.unibo.bls.devices.gui.ButtonAsGui;
import it.unibo.bls.utils.Utils;
import it.unibo.blsFramework.applLogic.BlsApplicationLogic;
import it.unibo.blsFramework.interfaces.IBlsFramework;
import it.unibo.blsFramework.kotlin.applLogic.AnotherApplLogic;
import it.unibo.bls.devices.gui.LedAsGui;


public class MainBlsFramework extends BlsFramework {
    public MainBlsFramework(String cmdName) {
        super( cmdName );
    }

	public static void main(String[] args) {
		IBlsFramework blSystem = BlsFramework.createTheSystem("BLSF");

		blSystem.setConcreteLed( LedAsGui.createLed() );
		//blSystem.addConcreteLed( LedAsGui.createLed() );
        //blSystem.addConcreteLed(LedProxyArduino.create(DeviceConfig.serialPortNum, DeviceConfig.serialBaudrate));
		blSystem.addConcreteButton( ButtonAsGui.createButton("LedControl") );
        //blSystem.addConcreteButton( new ButtonProxyArduino(DeviceConfig.serialPortNum, DeviceConfig.serialBaudrate));

		Utils.delay(5000);
		System.out.println(" ================== CHANGE CONTROL ================= ");
 		blSystem.setApplLogic (  new AnotherApplLogic() );

 	}
}

/*
	//Inject the concrete MOCK devices
		blSystem.setConcreteLed( LedMock.createLed() );
		blSystem.setConcreteButton( ButtonMock.createButton() );

	//Inject the concrete Led Arduino devices
		blSystem.setConcreteLed(LedProxy.createLed(DeviceConfig.serialPortNum, DeviceConfig.serialBaudrate));
		blSystem.setConcreteButton(ButtonAsGui.createButton("ClickMe"));

	//Inject the concrete Button Arduino devices
		blSystem.setConcreteLed( LedGui.createLed() );
		blSystem.setConcreteButton( new ButtonProxyArduino(DeviceConfig.serialPortNum, DeviceConfig.serialBaudrate));

	//CHANGE THE SYSTEM DYNAMICALLY BY ADDING TO A GUI LED AN ARDUINO LED
		blSystem.setConcreteLed( LedGui.createLed() );
		blSystem.setConcreteButton(ButtonAsGui.createButton("ClickMe"));
		Utils.delay(5000);
		blSystem.setConcreteLed(LedProxy.createLed(DeviceConfig.serialPortNum, DeviceConfig.serialBaudrate));
*/

