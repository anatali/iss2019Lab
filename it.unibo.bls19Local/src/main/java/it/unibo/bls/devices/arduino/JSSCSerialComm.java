package it.unibo.bls.devices.arduino;

import it.unibo.bls.utils.Utils;
import jssc.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class JSSCSerialComm implements SerialPortEventListener {
    private int rate = SerialPort.BAUDRATE_9600;	//115200 _9600
    private List<String> list;
    private String line = "";
    private Semaphore dataAvailable;
    private SerialPort serialPort;
    private Lock object;
    private String[] portNames;
    private boolean prefixEnded = false;
/*
We must set just one connection for PC-port
*/
    private static Map<String, JSSCSerialComm> curOpenPorts =
                            new HashMap<String, JSSCSerialComm>();

    public static JSSCSerialComm getSerialConn( String commPortName, int rate ) throws Exception{
        if( curOpenPorts.containsKey(commPortName)) return  curOpenPorts.get(commPortName);
        else{
            JSSCSerialComm conn = new JSSCSerialComm( rate );
            curOpenPorts.put(commPortName,conn);
            conn.connect( commPortName );
            return conn;
        }
    }

    public JSSCSerialComm( int rate ) {
        this.rate = rate;
        init();
    }

    protected void init(){
        list          = new ArrayList<String>();
        dataAvailable = new Semaphore(0);
        object        = new ReentrantLock();
        portNames     = SerialPortList.getPortNames();

        if (portNames.length == 0) {
            println("There are no serial-ports");
            return;
        }else{
            println("FOUND " + portNames.length + " serial-ports");
            for( int i=0; i<portNames.length;i++){
                println("FOUND " + portNames[i] + " PORT");
            }
        }
    }

    public void connect(String commPortName) throws Exception{
        serialPort = null;
        for (int i = 0; i < portNames.length; i++){
            if(portNames[i].equals(commPortName)){
                println("CONNECTING TO " + portNames[i] + " rate=" + rate );
                serialPort = new SerialPort(commPortName);
                serialPort.openPort();
                serialPort.setParams( rate,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);
 				serialPort.addEventListener(this, SerialPort.MASK_RXCHAR);
             }
        }
     }
     public void close() {
        try {
            serialPort.removeEventListener();
            serialPort.closePort();
        } catch (SerialPortException e) {
            println("could not close the port");
        }
    }
     public String readData() {
        String result = "";
        try{
            dataAvailable.acquire();
            object.lock();
            result = list.remove(0);
            object.unlock();
        } catch (Exception e){
            println("could not read from port");
        }
        return result;
    }

    public void writeData(String data) {
        try {
            serialPort.writeString(data);
        } catch (SerialPortException e) {
            println("could not write to port");
        }
    }

    public void serialEvent(SerialPortEvent event) {
        if(event.isRXCHAR() && event.getEventValue() > 0) {
            try {
                String data = serialPort.readString( event.getEventValue() );
                line += data;
                if( prefixEnded ){
                    //println("serialEvent line=" + line);
                    String[] ds  = line.split("\r");
                    if( ds.length > 1 ) {
                        String msg = ds[0].replace("\n","");
                        if( msg.length() > 0 ){
                            //println("serialEvent msg=" + msg);
                            //EMIT AN EVENT ... //GlobalScope.launch{ msgQueue.send(msg) }
                            object.lock();
                            list.add( msg );
                            object.unlock();
                            dataAvailable.release();
                        }
                        line = ds[1];
                    }
                    return;
                }

                if( line.contains("START@") ){
                    prefixEnded = true;
                    line = line.substring(line.indexOf('@')+1, line.length() );
                    //println("serialEvent firtMsg=" + line + " " + line.length());
                }
             }
            catch (SerialPortException ex) {
                println("Error in receiving from COM-port: " + ex);
            }
        }
    }
     private void println( String msg ){
        System.out.println("    JSSCSerialComm | " + msg );
    }

   /*
   FOR a rapid check ...
    */
    public static void main( String[] args) throws Exception{
        JSSCSerialComm conn = new JSSCSerialComm( SerialPort.BAUDRATE_9600 );
        conn.connect("COM5");
        Utils.delay(2000);  //Give the time to start
        /*
        //FOR LED
        for( int i=1; i<=3; i++ ) {
            Utils.delay(500);
            conn.writeData("1");
            Utils.delay(500);
            conn.writeData("0");
        }
        conn.close();
        */

        //FOR BUTTON


    }
}
