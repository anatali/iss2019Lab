package resources

import it.unibo.bls.devices.arduino.JSSCSerialComm


class ledProxyArduino( val  portNum : String,  val rate : Int) {
	companion object {
		lateinit var proxy : ledProxyArduino
		lateinit var conn  : JSSCSerialComm
		fun create( portNum: String,  rate: Int){
			proxy = ledProxyArduino(  portNum, rate )
		}
		
	}
	init{
		try {
			 conn = JSSCSerialComm.getSerialConn(portNum, rate)
		 } catch ( e : Exception ) {
            println("ERROR $e");
        }
	}
		fun turnOn(){
			conn.writeData("1");
		}
		fun turnOff(){
			conn.writeData("0");
		}
}