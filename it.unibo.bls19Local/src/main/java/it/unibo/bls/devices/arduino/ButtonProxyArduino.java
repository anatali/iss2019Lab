package it.unibo.bls.devices.arduino;

import it.unibo.bls.interfaces.IButtonObservable;
import it.unibo.bls.interfaces.IObserver;
import java.util.Observable;

public class ButtonProxyArduino extends Observable implements IButtonObservable {
    private JSSCSerialComm conn;

    public ButtonProxyArduino(String portNum, int rate ){
        configure( portNum, rate );
    }
    protected void configure( String portNum, int rate ){
        try {
            //conn = new JSSCSerialComm( rate );
            //conn.connect(portNum);

            conn = JSSCSerialComm.getSerialConn(portNum, rate);
            lookAtInputFromSerial(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override //from Observable and IButtonObservable
    public void addObserver(IObserver observer) {
        super.addObserver(observer);
    }
    //called by lookAtInputFromSerial thread
    public synchronized void update( String msg ){
         setChanged();
         notifyObservers( msg );
    }
    private void lookAtInputFromSerial(ButtonProxyArduino obj){
        new Thread(){
            public void run(){
                while( true ) {
                    String msg = conn.readData();  //blocking
                    //System.out.println("    ButtonProxyArduino | lookAtInputFromSerial: " + msg);
                    if( msg.contains("buttonstate(0)") ) {  //is a click //TODO: introduce a Message class
                        //System.out.println("    ButtonProxyArduino | lookAtInputFromSerial: " + msg);
                        obj.update(msg);
                    }

                }
            }
        }.start();
    }
}
