package itunibo.robotMbot;
//import java.util.Enumeration;
import java.util.Observable;		
import jssc.SerialPort;
import jssc.SerialPortException;
//import gnu.io.CommPortIdentifier;
//import gnu.io.SerialPort;
import it.unibo.is.interfaces.IOutputView;
import it.unibo.system.SituatedPlainObject;

public class SerialPortSupport extends SituatedPlainObject implements ISerialPortConnection {
 	private static final int DATA_RATE = 115200; //9600;
	protected SerialPort serialPort; 
	/*
	 *  "/dev/tty.usbserial-A9007UX1", // Mac OS X 
	 *  "/dev/ttyUSB0", // Linux
	 *  "COM31", "COM54" // Windows  
	 */
	public SerialPortSupport( IOutputView outView) {
		super(outView);
	}
	@Override
	public SerialPort connect(String portName, Class userClass ) throws Exception {
		return connect( portName, userClass.getName() );
	}
	@Override
	public SerialPort connect(String portName  ) throws Exception {
		return connect( portName, "" );
	}
	@Override
	public SerialPort connect(String portName, String name ) throws Exception {
//		CommPortIdentifier portId = null;
//		Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();
//		// First, Find an instance of serial port as set in PORT_NAMES.
//		while (portEnum.hasMoreElements()) {
//			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
//					.nextElement();
//			if (currPortId.getName().equals(portName)) {
//				portId = currPortId;
//				break;
//			}
//		}
//		String portId = serialPort.getPortName();
//		if (portId == null) {
//			throw new Exception("Could not find COM port");
//		}
 		 println("*** SerialPortSupport connect "+ name + " portName=" + portName + " datarate=" + DATA_RATE);
		// open serial port, and use class name for the appName.
//		serialPort = (SerialPort) portId.open( name, TIME_OUT);
		 serialPort = new SerialPort(portName);
		 serialPort.openPort();
		// set port parameters
//		serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
//				SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		serialPort.setParams(DATA_RATE, SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		return serialPort;
	}
	

	@Override
	public SerialPort getPort() {
		return serialPort;
	}
	@Override
	public void closeConnection(String portName) throws Exception {		
	} 
	@Override
	public void update(Observable arg0, Object arg1) {
	}
}
