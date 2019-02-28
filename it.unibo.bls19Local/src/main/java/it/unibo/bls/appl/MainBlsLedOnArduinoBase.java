package it.unibo.bls.appl;

import it.unibo.bls.devices.ButtonType;
import it.unibo.bls.devices.LedType;

public class MainBlsLedOnArduinoBase extends MainBlsMockBase{
 	public static MainBlsLedOnArduinoBase createTheSystem(){
 		return new MainBlsLedOnArduinoBase();	//calls the super costructor
 	}

	protected void setDeviceTypes(){
		ledType    = LedType.LedOnArduino;
		buttonType = ButtonType.ButtonGuiObj;
	}

 	public static void main(String[] args) {
 		createTheSystem();
 	}
}
