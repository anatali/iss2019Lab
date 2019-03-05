package it.unibo.bls.appl;

import it.unibo.bls.devices.ButtonType;
import it.unibo.bls.devices.LedType;

public class MainBlsGuiBase  extends MainBlsMockBase{
 	public static MainBlsGuiBase createTheSystem(){
 		return new MainBlsGuiBase();	//calls the super costructor
 	}

 	@Override  //From MainBlsMockBase
	protected void setDeviceTypes(){
		ledType    = LedType.LedGuiObj;
		buttonType = ButtonType.ButtonGuiObj;
	}

 	public static void main(String[] args) {
 		createTheSystem();
 	}
}
