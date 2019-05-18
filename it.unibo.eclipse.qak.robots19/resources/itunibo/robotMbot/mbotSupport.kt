package itunibo.robotMbot
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object mbotSupport{
	lateinit var actor   : ActorBasic
 	lateinit var conn    : SerialPortConnSupport
	var dataSonar        : Double = 0.0
	
	fun create( myactor: ActorBasic, port : String ){
		actor = myactor
		initConn( port )
	}
	
	fun move( cmd : String ){
		//println("mbotSupport move cmd=$cmd conn=$conn")
		when( cmd ){
			"msg(w)" -> conn.sendCmd("w")
			"msg(s)" -> conn.sendCmd("s")
			"msg(a)" -> conn.sendCmd("a")
			"msg(d)" -> conn.sendCmd("d")
			"msg(h)" -> conn.sendCmd("h")
		}
	}
	
	private fun initConn( port : String ){
		try {
			println("mbotSupport initConn starts")
			val serialConn = JSSCSerialComm()
			conn = serialConn.connect(port)	//returns a SerialPortConnSupport
			println("mbotSupport initConn conn= $conn")
			if( conn === null ) return;
			//while(true){
				val curDataFromArduino = conn.receiveALine()  //consume "start" sent by Arduino
				println("mbotSupport received: $curDataFromArduino"  )
				//if( curDataFromArduino == "start" ) break
			//}
			getDataFromArduino();
		}catch(  e : Exception) {
			println("mbotSupport ERROR ${e }"   );
		}		
	}
	
	private fun getDataFromArduino(   ) {
           GlobalScope.launch {
                while (true) {
 						try {
							var curDataFromArduino = conn.receiveALine();
// 	 						println("getDataFromArduino received:" + curDataFromArduino );
 							var v =    curDataFromArduino.toDouble() ;
							//handle too fast change
 							var delta =  Math.abs( v - dataSonar);
 							if( delta < 7 && delta > 0.5 ) {
								dataSonar = v;
								println("mbotSupport sonar:" + dataSonar);								
								actor.emit("sonar", "sonar( ${dataSonar.toInt()} )");
 							}
						} catch ( e : Exception) {
 							println("getDataFromArduino | ERROR $e   ")
 							//System.exit(1)
                    }
				}
			}
	}
}