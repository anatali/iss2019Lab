package it.unibo.bls.devices.arduino;

import it.unibo.bls.devices.mock.LedMock;
import it.unibo.bls.interfaces.ILed;
import jssc.SerialPort;

public class LedProxyArduino extends LedMock {
    private JSSCSerialComm conn;

    public static ILed create(String portNum, int rate){
        return new LedProxyArduino(  portNum, rate );
    }
    public LedProxyArduino( String portNum, int rate ){
        configure( portNum, rate );
    }

    protected void configure( String portNum, int rate ){
        try {
            //conn = new JSSCSerialComm( rate );
            //conn.connect(portNum);
            conn = JSSCSerialComm.getSerialConn(portNum, rate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override //LedMock
    public void turnOn(){
        super.turnOn();
        conn.writeData("1");
      }
    @Override //LedMock
    public void turnOff() {
        super.turnOff();
        conn.writeData("0");
    }
}
