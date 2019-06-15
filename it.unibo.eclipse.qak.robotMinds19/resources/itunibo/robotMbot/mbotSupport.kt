package itunibo.robotMbot
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import it.unibo.kactor.ActorBasicFsm
import it.unibo.kactor.MsgUtil

object mbotSupport{
	lateinit var actor   : ActorBasicFsm
 	lateinit var conn    : SerialPortConnSupport
	var dataSonar        : Int = 0 ; //Double = 0.0
	lateinit var filter  : ActorBasic
			
	fun create( myactor: ActorBasicFsm, port : String ){
		actor = myactor
		configureSonarPipe()
		initConn( port )
	}
	
	fun configureSonarPipe(){
		filter =  sonardatafilter("filter", actor )
		actor.subscribe(filter)
	}
	
	private fun initConn( port : String ){
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
			getDataFromArduino();
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
			"msg(h)" -> conn.sendALine("h")
			else -> println("mbotSupport command unknown")
		}
	}
	
	
	private fun emitDataAtModelLevel(dataSonar : Int ){
		actor.scope.launch{ actor.emit("sonarRobot", "sonar( ${ dataSonar } )")  }//MODEL LEVEL
	}
	private fun emitDataAtStreamLevel(dataSonar : Int ){
 		val event = MsgUtil.buildEvent(actor.name,"sonarRobot","sonar( $dataSonar )")
		actor.scope.launch{ actor.emitLocalStreamEvent(event)		} //STREAM LEVEL
	}
	private fun emitDataAtOopLevel(dataSonar : Int ){
		(filter as sonardatafilter).elabSonarData("$dataSonar") 	 //OOP LEVEL
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
								//actor.emit("sonarRobot", "sonar( ${ dataSonar } )") //MODEL LEVEL
							
								//TO EXPLOIT THE STREAM PIPE
 								//val event = MsgUtil.buildEvent(actor.name,"sonarRobot","sonar( $dataSonar )")
								//actor.emitLocalStreamEvent(event)		//STREAM LEVEL
							
//							    emitDataAtModelLevel(dataSonar)
//							    emitDataAtStreamLevel(dataSonar)
							//if( (filter as sonardatafilter).modeReact =="oop") emitDataAtOopLevel(dataSonar)		 
							if( (filter as sonardatafilter).modeReact =="pipe") emitDataAtStreamLevel(dataSonar)	 
							else if( (filter as sonardatafilter).modeReact =="model") emitDataAtModelLevel(dataSonar)
							else  emitDataAtOopLevel(dataSonar)
						} catch ( e : Exception) {
 							println("getDataFromArduino | ERROR $e   ")
 							//System.exit(1)
                    }
				}
			}
	}
}