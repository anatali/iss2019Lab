package it.unibo.bls.devices.arduino;

import it.unibo.bls.devices.mock.LedMock;
import jssc.SerialPort;

public class LedProxyArduino extends LedMock {
    private JSSCSerialComm conn;

    public LedProxyArduino( String portNum, int rate ){
        configure( portNum, rate );
    }

    protected void configure( String portNum, int rate ){
        try {
            conn = new JSSCSerialComm( rate );
            conn.connect(portNum);
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
