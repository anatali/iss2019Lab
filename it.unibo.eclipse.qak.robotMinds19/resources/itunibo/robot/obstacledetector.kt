package itunibo.robot

import it.unibo.kactor.ActorBasicFsm
import it.unibo.kactor.MsgUtil
 
class obstacledetector (name : String, val owner : ActorBasicFsm ,
						var LimitDistance : Int = 12 ) : ApplActorDataStream(name){
 	 
	init{ //USING A CONFIGURATION FILE
		this.solve( "consult(\"sonarDataConfig.pl\")" )
   	    solve("limitDistance(D)")
	    if( solveOk() ) LimitDistance = Integer.parseInt( getCurSol("D").toString() )
 	}
	
	override protected suspend fun elabData(data : String ){
		val Distance = Integer.parseInt( data )
// 		println("   name |  obstacledetector Distance = $Distance ")
		detectObstacle( Distance )
  	}
	
 	suspend fun detectObstacle( Distance : Int ){
		if( Distance < LimitDistance ){
 			val m1 = MsgUtil.buildEvent(name, "obstacle", "obstacle($Distance)")
//			println("   name |  obstacledetector detectObstacle emit m1= $m1 ")
 			emitLocalStreamEvent( m1 )  //PROPAGATE to the pipe
			owner.emit( m1 ) //PROPAGATE at application level MUST HAVE A CONTEXT!!!
		}
	}
	
}