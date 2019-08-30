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

			robotDataSourceArduino("robotDataSourceArduino", owner, filter, conn)
		}catch(  e : Exception) {
			println("mbotSupport ERROR ${e }"   );
		}		
	}
	
	/*
 	 Aug 2019
     The moves l, r, z, x are executed (at the moment)
 	  by the Python application robotCmdExec that exploits GY521
    */
	fun  move( cmd : String ){
		//println("mbotSupport move cmd=$cmd conn=$conn")
		when( cmd ){
			"msg(w)" -> conn.sendALine("w")
			"msg(s)" -> conn.sendALine("s")
			"msg(a)" -> conn.sendALine("a")
			"msg(d)" -> conn.sendALine("d")
			"msg(l)" -> sendToPython("l")	//conn.sendALine("l")
			"msg(r)" -> sendToPython("r")	//conn.sendALine("r")
			"msg(z)" -> sendToPython("z")	//conn.sendALine("z")
			"msg(x)" -> sendToPython("x")	//conn.sendALine("x")
			"msg(h)" -> conn.sendALine("h")
			else -> println("mbotSupport command unknown")
		}
		
	}
	
	private fun sendToPython( msg : String ){
		println("mbotSupport sendToPython $msg")
		owner.scope.launch{ owner.emit("rotationCmd","rotationCmd($msg)") }
	}
	
	/* MOVED INTO robotDataSourceArduino	
	private fun emitDataAtModelLevel(dataSonar : Int ){
		...
 	private fun getDataFromArduino(   ) {
  */
}