package it.unibo.blsFramework.concreteDevices.arduino;

import it.unibo.bls.devices.arduino.JSSCSerialComm;
import it.unibo.bls.interfaces.IObserver;
import java.util.Observable;

public class LedProxy implements IObserver  {
    private JSSCSerialComm conn;

    public static IObserver createLed(String portNum, int rate) {
        return new LedProxy(portNum, rate);
    }
    public LedProxy(String portNum, int rate) {
        configure(portNum, rate);
    }
    protected void configure(String portNum, int rate) {
        try {
            conn = new JSSCSerialComm(rate);
            conn.connect(portNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override //IObserver
    public void update(Observable o, Object arg) {
        boolean state = arg.toString().equals("true");
        if( state ) turnOn(); else turnOff();
    }
    public void turnOn() {
        conn.writeData("1");
    }
    public void turnOff() {
        conn.writeData("0");
    }
}