package it.unibo.blsFramework.models;

import java.util.Observable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.blsFramework.interfaces.ILedObservable;

/*
 * LedModel is an observable logical model of the led
 */
public class LedModel extends Observable implements ILedObservable {
private boolean state = false;

//Factory method
public static ILedObservable createLed(){
	return new LedModel();
}
public static ILedObservable createLed(IObserver observer){
	ILedObservable led = new LedModel();
	led.addObserver(observer);
	return led;
}

@Override
public void turnOn(){
	state = true;
	update();
}
@Override
public void turnOff() {
	state = false;
	update();
}
@Override
public boolean getState(){
	return state;
}

protected void update() {
	System.out.println(" LedModel update state=" + state );
	this.setChanged();
	this.notifyObservers(""+state);		//Always a String
}

@Override
public void addObserver(IObserver observer) {
	if( observer != null ) super.addObserver(observer);
}

}
