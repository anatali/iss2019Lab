package it.unibo.blsFramework.concreteDevices;
import it.unibo.bls.interfaces.ILed;
import it.unibo.blsFramework.interfaces.ILedObserver;
import java.util.Observable;

public class LedObserver implements ILedObserver {
private ILed led;

    public static ILedObserver create( ){
        return new LedObserver(   );
    }
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
