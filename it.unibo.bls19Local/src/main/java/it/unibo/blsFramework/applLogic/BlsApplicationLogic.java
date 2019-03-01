package it.unibo.blsFramework.applLogic;

import java.util.Observable;
import it.unibo.bls.interfaces.ILed;
import it.unibo.bls.interfaces.IObserver;

public class BlsApplicationLogic implements IObserver{
	private ILed led;
	private int count = 0;
	
	public BlsApplicationLogic(ILed led){
		this.led = led;
	}
	public void execute( ){
		count++;
		if( led == null ) return;
		if( led.getState() ) led.turnOff();
		else led.turnOn();
		System.out.println("BlsApplicationLogic ledstate=" + led.getState() + " count=" + count);
	}
	@Override
	public void update(Observable source, Object val) {
		System.out.println("BlsApplicationLogic update:"  + val);
 		execute();
	}
	
	public int getNumOfActivations() {
		return count;
	}
}
