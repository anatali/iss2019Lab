package it.unibo.bls.devices.mock;

import it.unibo.bls.interfaces.ILed;

public class LedMock implements ILed{
protected boolean state = false;

//Factory method
public static ILed createLed(){
	return new LedMock();
}
@Override
public void turnOn(){
	state = true;
	showState();
}
@Override
public void turnOff() {
	state = false;
	showState();
}
@Override
public boolean getState(){
	return state;
}

private void showState(){
	System.out.println("Led state=" + state);
}
}
