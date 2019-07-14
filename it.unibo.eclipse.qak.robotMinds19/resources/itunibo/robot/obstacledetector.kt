package itunibo.robot

import it.unibo.kactor.ActorBasicFsm
import it.unibo.kactor.MsgUtil
 
class obstacledetector (name : String, //val owner : ActorBasicFsm ,
						var LimitDistance : Int = 12 ) : ApplActorDataStream(name){
 	var maxDelta   = 0
	var previusVal = 0
	init{ //USING A CONFIGURATION FILE
		this.solve( "consult(\"sonarDataConfig.pl\")" )
   	    solve("limitDistance(D)")
	    if( solveOk() ) LimitDistance = Integer.parseInt( getCurSol("D").toString() )
   	    solve("maxDelta(V)")
	    if( solveOk() ) maxDelta = Integer.parseInt( getCurSol("V").toString() )
 	}
	
	override protected suspend fun elabData(data : String ){
		val Distance = Integer.parseInt( data )
 		//println("   $name |  obstacledetector Distance = $Distance ")
		detectObstacle( Distance )
  	}
	
 	suspend fun detectObstacle( Distance : Int ){
		if( Distance < LimitDistance  ){ //&& Math.abs(previusVal-Distance) >=  maxDelta
			previusVal = Distance
 			val m1 = MsgUtil.buildEvent(name, "obstacle", "obstacle($Distance)")
 			println("   $name |  detectObstacle emit m1= $m1 ")
 			emitLocalStreamEvent( m1 )  //PROPAGATE to the pipe
			//PROPAGATE at application level MUST HAVE A CONTEXT!!!
//			owner.emit( m1 ) 
		}
	}
	
}