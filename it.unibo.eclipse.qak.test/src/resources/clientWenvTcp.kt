package resources

import it.unibo.kactor.*
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import org.json.JSONObject
import alice.tuprolog.*
 

    class clientWenvTcp( name : String, scope: CoroutineScope) : ActorBasic(name,scope){
        private var hostName = "localhost"
        private var port     = 8999
        private val sep      = ";"
        private var outToServer: PrintWriter?     = null
        private var inFromServer: BufferedReader? = null
/* 
        companion object {
          private lateinit var worker : clientWenvTcp
		  private lateinit var  owner : ActorBasic
			
          fun create(  a : ActorBasic ){
			  owner  = a
 			  worker = clientWenvTcp()
 		  }
		  fun sendMsg( v: String ) {
				GlobalScope.launch{ MsgUtil.sendMsg("send",v,worker) }
		  }
        }//companion object
*/
 
        init{
			 println("clientWenvTcp | STARTS   ")
             initClientConn()
        }

        fun initClientConn(hostName: String = "localhost", portStr: String = "8999"  ) {
            //hostName         = hostNameStr
            port             = Integer.parseInt(portStr)
            try {
                val clientSocket = Socket(hostName, port)
                println("clientWenvTcp |  CONNECTION DONE")
                inFromServer = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                outToServer  = PrintWriter(clientSocket.getOutputStream())
                startTheReader()
            }catch( e:Exception ){
                println("clientWenvTcp | ERROR $e")
            }
        }

        override suspend fun actorBody(msg: ApplMessage) {
            //println("clientWenvTcp | receives $msg   ")
            when( msg.msgId() ){
                "start" -> initClientConn()
                "send"  -> sendMsg( msg.msgContent() )
                //else -> println("clientWenvTcp $msg UNKNOWN ")
            }
        } 

        fun sendMsg(v: String) {
			//println("clientWenvTcp | sending Msg $v   ")
			var outS = "{'type': 'alarm', 'arg': 0 }"
			val t = Term.createTerm(v) as Struct
			val ts = t.getArg(0).toString()
			when( ts ){
				"moveforward"   -> outS = "{'type': 'moveForward',  'arg': -1 }"
    			"movebackward"  -> outS = "{'type': 'moveBackward', 'arg': -1 }"
				"moveleft"      -> outS = "{'type': 'turnLeft', 'arg': 400 }"
 				"moveright"     -> outS = "{'type': 'turnRight', 'arg': 400 }"
   			     "stop"         -> outS = "{'type': 'alarm', 'arg': 0 }"
 			}
			val jsonObject = JSONObject(outS)
			val msg= "$sep${jsonObject.toString()}$sep"
			//println("clientWenvTcp | sendMsg $msg   ")
			outToServer?.println(msg)
            outToServer?.flush()
         }

        private fun startTheReader(   ) {
            GlobalScope.launch {
                while (true) {
                    try {
                        val inpuStr = inFromServer?.readLine()
                        val jsonMsgStr =
                            inpuStr!!.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                        //println("clientWenvTcp | inpuStr= $jsonMsgStr")
                        val jsonObject = JSONObject(jsonMsgStr)
                        //println( "type: " + jsonObject.getString("type"));
                        when (jsonObject.getString("type")) {
                            "webpage-ready" -> println("webpage-ready ")
                            "sonar-activated" -> {
                                //println("sonar-activated ")
                                val jsonArg = jsonObject.getJSONObject("arg")
                                val sonarName = jsonArg.getString("sonarName")
                                val distance = jsonArg.getInt("distance")
                                //println("clientWenvTcp | sonarName=$sonarName distance=$distance")
                                val m = MsgUtil.buildEvent("tcp", sonarName,"$sonarName($distance)" )
                                emitLocalStreamEvent( m )
                                //emit( m )
								val m1 = "sonar($sonarName,clientWenvTcp,$distance)"
								//println( "clientWenvTcp EMIT $m1"   );
							 emit("sonar", m1);
								/*
 "sonar(NAME, player, DISTANCE)"
										.replace("NAME", sonarName
										.replace("-", ""))
										.replace("DISTANCE", (""+distance) )*/
                            }
                            "collision" -> {
                                //println( "collision"   );
                                val jsonArg = jsonObject.getJSONObject("arg")
                                val objectName = jsonArg.getString("objectName")
                                //println("clientWenvTcp | collision objectName=$objectName")
                                val m = MsgUtil.buildEvent( "tcp", "collision","collision($objectName)")
								//println("clientWenvTcp | emit $m")
                                emitLocalStreamEvent( m )
                                //emit( m )
 							 emit("sonarDetect","sonarDetect(TARGET)"
									.replace("TARGET", objectName
									.replace("-", "")));
                           }
                        }
                    } catch (e: IOException) {
                        //e.printStackTrace()
						println("clientWenvTcp | ERROR $e   ")
						System.exit(1)
                    }
                }
            }
         }//startTheReader
}//clientWenvTcp


