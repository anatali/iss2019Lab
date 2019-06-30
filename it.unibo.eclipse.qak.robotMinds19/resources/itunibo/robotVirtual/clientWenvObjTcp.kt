package itunibo.robotVirtual

import it.unibo.kactor.*
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import org.json.JSONObject
import alice.tuprolog.*
import itunibo.robotMbot.sonardatafilter

    object clientWenvObjTcp {
        private var hostName = "localhost"
        private var port     = 8999
        private val sep      = ";"
        private var outToServer: PrintWriter?     = null
        private var inFromServer: BufferedReader? = null

		lateinit var filter  : sonardatafilter   //JUNE2019
		lateinit var myactor : ActorBasicFsm	 //JUNE2019
		
        fun initClientConn(actor:ActorBasicFsm, hostName: String = "localhost", portStr: String = "8999"  ) {
            port    = Integer.parseInt(portStr)
			myactor = actor //JUNE2019
            try {
                val clientSocket = Socket(hostName, port)
                println("clientWenvObjTcp |  CONNECTION DONE")
                inFromServer = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                outToServer  = PrintWriter(clientSocket.getOutputStream())
				
				configureSonarPipe( )	//JUNE2019
				
                startTheReader(   )
            }catch( e:Exception ){
                println("clientWenvObjTcp | ERROR $e")
            }
        }

	fun configureSonarPipe( ){
		filter =  sonardatafilter("filter", myactor )
		if( filter.modeReact =="pipe"){
			myactor.subscribe(filter)
		}
	}
	private fun emitDataAtModelLevel(dataSonar : Int ){
		println("emitDataAtModelLevel $dataSonar")
//		actor.scope.launch{ actor.emit("sonarRobot", "sonar( ${ dataSonar } )")  }//MODEL LEVEL
		myactor.scope.launch{
			myactor.forward("modelChange", " modelChange(sonarRobot,$dataSonar)", "resourcemodel")
		}
	}
	private fun emitDataAtStreamLevel(dataSonar : Int ){
 		val event = MsgUtil.buildEvent(myactor.name,"sonarRobot","sonar( $dataSonar )")
		myactor.scope.launch{ myactor.emitLocalStreamEvent(event)		} //STREAM LEVEL
	}
	private fun emitDataAtOopLevel(dataSonar : Int ){
		println("emitDataAtOopLevel $dataSonar")
		filter.elabSonarData("$dataSonar") 	 //OOP LEVEL => emits obstacle
	}
 
        fun sendMsg(v: String) {
			//println("clientWenvObjTcp | sending Msg $v   ")
			var outS = "{'type': 'alarm', 'arg': 0 }"
			val t = Term.createTerm(v) as Struct
			val ts = t.getArg(0).toString()
			when( ts ){
				"w"   -> outS = "{'type': 'moveForward',  'arg': -1 }"
    			"s"  -> outS = "{'type': 'moveBackward', 'arg': -1 }"
				"a"  -> outS = "{'type': 'turnLeft', 'arg': 400 }"
 				"d"  -> outS = "{'type': 'turnRight', 'arg': 400 }"
				"l"  -> outS = "{'type': 'turnLeft', 'arg': 400 }"
 				"r"  -> outS = "{'type': 'turnRight', 'arg': 400 }"
   			    "h"  -> outS = "{'type': 'alarm', 'arg': 0 }"
 			}
			val jsonObject = JSONObject(outS)
			val msg= "$sep${jsonObject.toString()}$sep"
			//println("clientWenvObjTcp | sendMsg $msg   ")
			outToServer?.println(msg)
            outToServer?.flush()
         }

        private fun startTheReader(    ) {
            GlobalScope.launch {
                while (true) {
                    try {
                      //println("clientWenvObjTcp | READLINE")
                       val inpuStr = inFromServer?.readLine()
                        val jsonMsgStr =
                            inpuStr!!.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                        //println("clientWenvObjTcp | inpuStr= $jsonMsgStr")
                        val jsonObject = JSONObject(jsonMsgStr)
                        //println( "type: " + jsonObject.getString("type"));
                        when (jsonObject.getString("type")) {
                            "webpage-ready" -> println("webpage-ready ")
                            "sonar-activated" -> {
                                //println("sonar-activated ")
                                val jsonArg   = jsonObject.getJSONObject("arg")
                                val sonarName = jsonArg.getString("sonarName")
                                val distance  = jsonArg.getInt("distance")
  								val m1 = "sonar($sonarName, $distance)"
								//println( "clientWenvObjTcp EMIT $m1"   );
							    myactor.emit("sonar",m1 );
								
                           }
                            "collision" -> { 
                                //val jsonArg = jsonObject.getJSONObject("arg")
                                //val objectName = jsonArg.getString("objectName")
                                //println("clientWenvObjTcp | collision objectName=$objectName  myactor=${myactor.name}" )
   							     myactor.emit("sonarRobot","sonar(5)")
								
								//JUNE 2019 (streaming)
// 								val dataSonar = 5;  //The virtual robot is just sensible to physical CONTACT
//	  							if( filter.modeReact =="pipe") emitDataAtStreamLevel(dataSonar)	 
//								else if( filter.modeReact =="model") emitDataAtModelLevel(dataSonar)
//								else  emitDataAtOopLevel(dataSonar)  //default
                          }
                        }
                    } catch (e: IOException) {
                        //e.printStackTrace()
						println("clientWenvObjTcp | ERROR $e   ")
						System.exit(1)
                    }
                }
            }
         }//startTheReader
}//clientWenvObjTcp


