package itunibo.robotVirtual

import itunibo.robot.ApplActorDataStream
import kotlinx.coroutines.CoroutineScope
import org.json.JSONObject
import java.io.BufferedReader
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.launch
import it.unibo.kactor.ActorBasicFsm
import kotlinx.coroutines.delay

class robotDataSource(name : String, val owner : ActorBasicFsm , val filter : ActorBasic?,
					  val inFromServer: BufferedReader?) : ApplActorDataStream(name,owner.scope){
	
var obstacleName = ""
val event = MsgUtil.buildEvent(name,"sonarRobot","sonar( 5 )")
	
	init{
		scope.launch{  autoMsg("start","start(1)") }
		if( filter != null ) subscribe(filter)
	}
	
	override suspend fun elabData(data : String ){
                while (true) {
                    try {
                        val inpuStr = inFromServer?.readLine()
                       //println( "robotDataSource | inpuStr= $inpuStr")
                       val jsonMsgStr =
                            inpuStr!!.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1] 
                         val jsonObject = JSONObject(jsonMsgStr)
						val arg = jsonObject.get("arg").toString()
						//println( "robotDataSource | arg= ${arg}  ${arg.equals(obstacleName) }"   )
						if( arg.equals(obstacleName) ) {
							emitLocalStreamEvent(event)
						}
						else{
	                        when (jsonObject.getString("type")) {
	                            "webpage-ready" -> println("webpage-ready ")
	                            "sonar-activated" -> {
	                                //println("sonar-activated ")
	                                val jsonArg   = jsonObject.getJSONObject("arg")
	                                val sonarName = jsonArg.getString("sonarName")
	                                val distance  = jsonArg.getInt("distance")
	  								val m1 = "sonar($sonarName, $distance)"
									//println( "robotDataSource EMIT $m1"   );
	 								emitLocalStreamEvent(m1) 
	                           }
	                            "collision" -> {
									obstacleName = jsonObject.get("arg").toString()
									emitLocalStreamEvent(event)
	                           }				    
	                        }
						}
						
                    } catch (e: Exception) {
                        //e.printStackTrace()
						println("robotDataSource | ERROR $e   ")
						System.exit(1)
                    }
                }
		
	}
}