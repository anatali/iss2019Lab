package it.unibo.blsFramework.applLogic;

import it.unibo.bls.interfaces.ILed;
import it.unibo.bls.utils.Utils;
import it.unibo.blsFramework.interfaces.IAppLogic;
import it.unibo.blsFramework.interfaces.ILedModel;

public class BlsApplicationLogic implements IAppLogic { //Note: it is NOT an observer
	private ILed led;
	private int numOfCalls        = 0;
	private boolean doBlink       = false;
	private final Object monitor  = new Object();
	private boolean stopped       = false;

 	public void setControlled( ILedModel led ){ //no controlled => no activity
		this.led = led;
		doBlinkTheLedWaiting();		//Avoid polling on doBlink (numOfCalls)
	}

	protected void switchTheLed(){
		if( led == null ) return;		//defensive programming
		if( led.getState() ) led.turnOff(); else led.turnOn();
	}

	@Override //from IControlLed
	public void execute( String cmd  ){
		System.out.println("	BlsApplicationLogic | execute cmd=" + cmd);
 		if( cmd.equals("stop") ){ //to allow change in ApplicationLogic
			doBlink = false;
			return;
		}
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
						while ( true  ) {
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
