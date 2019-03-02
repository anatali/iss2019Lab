package it.unibo.blsFramework.concreteDevices.mock;

import it.unibo.bls.interfaces.IObserver;

import java.util.Observable;

public class LedMock implements  IObserver {
private boolean state = false;

//Factory method
public static IObserver createLed(){
	return new LedMock();
}

	public void update(Observable source, Object state){
		showState(state.toString());
	}

	private void showState(String state){
		System.out.println("	LedMock | state=" + state);
	}
}
