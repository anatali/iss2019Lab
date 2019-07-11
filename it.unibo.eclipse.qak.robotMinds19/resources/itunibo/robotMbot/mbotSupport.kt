package itunibo.robotMbot
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import it.unibo.kactor.ActorBasicFsm
import it.unibo.kactor.MsgUtil

object mbotSupport{
	lateinit var owner   : ActorBasicFsm
 	lateinit var conn    : SerialPortConnSupport
	var dataSonar        : Int = 0 ; //Double = 0.0
//	lateinit var filter  : sonardatafilter
			
	fun create( owner: ActorBasicFsm, port : String, filter : ActorBasic? ){
		this.owner = owner
		initConn( port, filter )
	}
	
	private fun initConn( port : String, filter : ActorBasic? ){
		try {
			println("mbotSupport initConn starts")
			val serialConn = JSSCSerialComm()
			conn = serialConn.connect(port)	//returns a SerialPortConnSupport
			println("mbotSupport initConn conn= $conn")
						
//			if( conn === null ) return;
//			while(true){ 
//				val curDataFromArduino = conn.receiveALine()  //consume "start" sent by Arduino
//				val istart = curDataFromArduino.contains("start")
//				println("mbotSupport initConn received: $curDataFromArduino istart=$istart "   )
//				if( istart ) break
//			}

			//			getDataFromArduino();
			robotDataSourceArduino("robotDataSourceArduino", owner, filter, conn)
		}catch(  e : Exception) {
			println("mbotSupport ERROR ${e }"   );
		}		
	}
	
	fun move( cmd : String ){
		//println("mbotSupport move cmd=$cmd conn=$conn")
		when( cmd ){
			"msg(w)" -> conn.sendALine("w")
			"msg(s)" -> conn.sendALine("s")
			"msg(a)" -> conn.sendALine("a")
			"msg(d)" -> conn.sendALine("d")
			"msg(l)" -> conn.sendALine("l")
			"msg(r)" -> conn.sendALine("r")
			"msg(h)" -> conn.sendALine("h")
			else -> println("mbotSupport command unknown")
		}
	}
	
	
	/* MOVED INTO robotDataSourceArduino	
	private fun emitDataAtModelLevel(dataSonar : Int ){
		owner.scope.launch{ owner.emit("sonarRobot", "sonar( ${ dataSonar } )")  }//MODEL LEVEL
	}
	private fun emitDataAtStreamLevel(dataSonar : Int ){
 		val event = MsgUtil.buildEvent(owner.name,"sonarRobot","sonar( $dataSonar )")
		owner.scope.launch{ owner.emitLocalStreamEvent(event)		} //STREAM LEVEL
	}
	private fun emitDataAtOopLevel(dataSonar : Int ){
		filter.elabSonarData("$dataSonar") 	 //OOP LEVEL
	}

	private fun getDataFromArduino(   ) {
           GlobalScope.launch {
                while (true) {
 						try {
							var curDataFromArduino = conn.receiveALine()
							globalTimer.startTimer()  //TIMER ....
 	 						//println("getDataFromArduino received: $curDataFromArduino"    )
 							var v = curDataFromArduino.toDouble() 
							//handle too fast change ?? NOT HERE
  							dataSonar = v.toInt();
							
							//println("mbotSupport sonar: ${ dataSonar }"   );								
// 							if( filter.modeReact =="pipe") emitDataAtStreamLevel(dataSonar)	 
//							else if( filter.modeReact =="model") emitDataAtModelLevel(dataSonar)
//							else  emitDataAtOopLevel(dataSonar)  //default
							
						    //JUNE 2019 (streaming)
							val event = MsgUtil.buildEvent(owner.name,"sonarRobot","sonar( $dataSonar )")								
							owner.scope.launch{ owner.emitLocalStreamEvent(event) }
							
						} catch ( e : Exception) {
 							println("getDataFromArduino | ERROR $e   ")
 							//System.exit(1)
                    }
				}
			}
	}
 */
}