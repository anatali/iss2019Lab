package itunibo.robotMbot;

//import java.util.ArrayList;
//import it.unibo.phisicalrobot.IRobotSerialObserver;
//import it.unibo.supports.serial.JSSCSerialComm;
//import it.unibo.supports.serial.SerialPortConnSupport;

public class RobotSerialCommunication {

	private SerialPortConnSupport conn = null;
	private JSSCSerialComm serialConn;
	private double dataSonar = 0;
	private String curDataFromArduino;
//	private ArrayList<IRobotSerialObserver> observer = new ArrayList<>();
	private Logger logger;
	private String port;

	public RobotSerialCommunication(String port, Logger logger) {
		this.logger = logger;
		this.logger.setOwner("RobotSerialCommunication");
		this.port = port;
		try {
			logger.log("start");
			serialConn = new JSSCSerialComm();  //new JSSCSerialComm(null); //
			conn = serialConn.connect(port); // returns a SerialPortConnSupport
			if (conn == null)
				return;
			curDataFromArduino = conn.receiveALine();
			logger.log("startup received: " + dataSonar);
			getDataFromArduino();
		} catch (Exception e) {
			logger.log("ERROR " + e.getMessage());
		}
	}
	
	public void close() {
		this.logger.log("closing communication on port " + port);
		try {
			conn.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void executeTheCommand(String cmd) {
		logger.log("executeTheCommand " + cmd + " conn=" + conn);
		try {
			//serialConn.writeData(cmd);
			conn.sendALine(cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public void addObserverToSensors(IRobotSerialObserver observer) {
//		this.observer.add(observer);
//	}

	private void getDataFromArduino() {
		new Thread() {
			public void run() {
				try {
					logger.log("getDataFromArduino STARTED");
					while (true) {
						try {
							curDataFromArduino = conn.receiveALine();
  	 						logger.log("mbotConnArduinoObj received1:" + curDataFromArduino );
							double v = Double.parseDouble(curDataFromArduino);
							// handle too fast change
							double delta = Math.abs(v - dataSonar);
							if (delta < 7 && delta > 1.0) {
								dataSonar = v;
								logger.log("mbotConnArduinoObj received2:" + curDataFromArduino );
//								observer.forEach(o -> o.notify("" + dataSonar));
//								QActorUtils.raiseEvent(curActor, curActor.getName(), "realSonar", 
//										"sonar( DISTANCE )".replace("DISTANCE", ""+dataSonar ));
							}
						} catch (Exception e) {
							logger.log("ERROR:" + e.getMessage());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public interface Logger {
		void setOwner(String owner);
		void log(String msg);
	}

	public static void main(String[] args) throws InterruptedException {
//		String port = args[0];
//		System.out.println("port="+port);   //   /dev/ttyUSB0
		RobotSerialCommunication robotSupport = new RobotSerialCommunication("/dev/ttyUSB0", new RobotSerialCommunication.Logger() {
			private String owner = "";
			@Override
			public void setOwner(String owner) {
				this.owner = owner + " ";
			}
			
			@Override
			public void log(String msg) {
				System.out.println(owner + msg);
			}
		});		
		robotSupport.executeTheCommand("w");
		Thread.sleep(2000);
		robotSupport.executeTheCommand("h");
		System.out.println("DONE " ); 
	}


}

