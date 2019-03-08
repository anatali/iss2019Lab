package it.unibo.blsFramework.kotlin.fsm;

import it.unibo.bls.interfaces.ILed;
import it.unibo.blsFramework.interfaces.ILedObserver;

import java.util.Observable;

public class LedObserverAdapter implements ILedObserver {
private ILed led;


    @Override //ILedObserver - IObserver
    public void update(Observable o, Object arg) {
        boolean newState = arg.toString().equals("true");
        if( led != null && newState ) led.turnOff(); else led.turnOn();
    }
    @Override //ILedObserver
    public void setLed( ILed led ){
        this.led = led;
    }
}
