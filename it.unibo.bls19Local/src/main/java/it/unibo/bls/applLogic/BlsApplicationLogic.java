package it.unibo.bls.applLogic;

import it.unibo.bls.interfaces.IControlLed;
import it.unibo.bls.interfaces.ILed;
import it.unibo.bls.utils.Utils;

public class BlsApplicationLogic implements IControlLed { //Note: it is NOT an observer
	private ILed led;
	private int numOfCalls = 0;
	private boolean doBlink = false;
	private final Object monitor = new Object();

 	public void setControlled( ILed led ){ //no controlled => no activity
		this.led = led;
		//doBlinkTheLed();
		doBlinkTheLedWaiting();		//Avoid polling on doBlink (numOfCalls)
	}
	/*
	NAIVE solution based on polling on numOfCalls
	 */
	private void doBlinkTheLed(){
		new Thread(){
			public void run(){
 				System.out.println("	BlsApplicationLogic | doBlinkTheLed Thread STARTS looping ...");
					while (true) {
						if ( doBlink ) switchTheLed(); //else do nothing
						Utils.delay(250);
					}
			}
		}.start();
	}

	private void switchTheLed(){
		if( led == null ) return;		//defensive programming
		if( led.getState() ) led.turnOff(); else led.turnOn();
	}

	@Override //from IControlLed
	public void execute( String cmd  ){
		numOfCalls++;
		doBlink = numOfCalls % 2 != 0;
		System.out.println("	BlsApplicationLogic | numOfCalls=" + numOfCalls + " doBlink=" + doBlink);
		synchronized( monitor ) {	//see doBlinkTheLedWaiting
 			monitor.notify();
		}
	}

 	//Useful for testing
 	public int getNumOfCalls() {
		return numOfCalls;
	}

	/*
	AVOID POLLING
	 */
	private void doBlinkTheLedWaiting(){
		new Thread(){
			public void run(){
 				System.out.println("	BlsApplicationLogic | doBlinkTheLedWaiting Thread STARTS   ..."  );
				try {
						while (true) {
							synchronized( monitor ) {
								while( ! doBlink ) monitor.wait();
							}
							switchTheLed();
							Utils.delay(250);
						}
  				}catch(Exception e){}
			}
		}.start();
	}
}
