package itunibo.robotMbot

import it.unibo.kactor.ActorBasicFsm
import itunibo.robot.ApplActorDataStream
import kotlinx.coroutines.launch
import java.io.BufferedReader
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil

class  robotDataSourceArduino( name : String, val owner : ActorBasicFsm , val filter : ActorBasic?,
		  val conn    : SerialPortConnSupport //,val inFromServer: BufferedReader?
		  ) : ApplActorDataStream(name,owner.scope){
	
	
	init{
		scope.launch{  autoMsg("start","start(1)") }
		if( filter != null ) subscribe(filter)
	}

	override suspend fun elabData(data : String ){
                while (true) {
 						try {
							var curDataFromArduino = conn.receiveALine()
							//globalTimer.startTimer()  //TIMER ....
 	 						//println("getDataFromArduino received: $curDataFromArduino"    )
 							var v = curDataFromArduino.toDouble() 
							//handle too fast change ?? NOT HERE
  							var dataSonar = v.toInt();							
							//println("mbotSupport sonar: ${ dataSonar }"   );								
						    //JUNE 2019 (streaming)
							val event = MsgUtil.buildEvent( name,"sonarRobot","sonar( $dataSonar )")								
							//owner.scope.launch{ owner.emitLocalStreamEvent(event) }
							emitLocalStreamEvent(event)
						} catch ( e : Exception) {
 							println("getDataFromArduino | ERROR $e   ")
 							//System.exit(1)
                    }
				}
	}
	
 
}