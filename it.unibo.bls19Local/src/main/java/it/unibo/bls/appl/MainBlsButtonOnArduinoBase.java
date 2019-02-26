package it.unibo.bls.appl;

import it.unibo.bls.devices.ButtonType;
import it.unibo.bls.devices.LedType;

public class MainBlsButtonOnArduinoBase extends MainBlsMockBase{
 	public static MainBlsButtonOnArduinoBase createTheSystem(){
 		return new MainBlsButtonOnArduinoBase();	//calls the super costructor
 	}

	protected void setDeviceTypes(){
		ledType    = LedType.LedGuiObj;
		buttonType = ButtonType.ButtonOnArduino;
	}

 	public static void main(String[] args) {
 		createTheSystem();
 	}
}
