package it.unibo.bls.applLogic;

import java.util.Observable;
import it.unibo.bls.interfaces.IControl;
import it.unibo.bls.interfaces.ILed;
import it.unibo.bls.utils.Utils;

public class BlsApplicationLogic implements IControl {
	private ILed led;
	private int numOfCalls = 0;
	private boolean doBlink = false;
	private final Object monitor = new Object();

	public void setTheLed(ILed led){
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

	@Override //from IControl
	public void execute( String cmd  ){
		updateNumOfCalls();
	}

 	protected void updateNumOfCalls(){
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
					synchronized( monitor ) {
						while (true) {
							while( ! doBlink ) monitor.wait();
							switchTheLed();
							Utils.delay(250);
						}
					}
  				}catch(Exception e){}
			}
		}.start();
	}


}
