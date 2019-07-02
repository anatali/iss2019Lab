package itunibo.robot

import it.unibo.kactor.ActorBasicFsm
import it.unibo.kactor.MsgUtil
 

class sonaractorfilter (name : String, val owner: ActorBasicFsm ) : ActorDataStream(name){
    private var LimitDistance  = 12
	private var LastDistance = 0
	private var minDistance  = 2
	private var maxDistance  = 50
	private var maxDelta     = 1
	private var amplif       = 6	//radar does D/3
 	 
	init{
		println("   $name | owner= ${owner.name}")
    	owner.solve("limitDistance(D)")
	    LimitDistance = Integer.parseInt( owner.getCurSol("D").toString() )
    	owner.solve("minDistance(D)")
	    minDistance = Integer.parseInt( owner.getCurSol("D").toString() )
    	owner.solve("maxDistance(D)")
	    maxDistance = Integer.parseInt( owner.getCurSol("D").toString() )
    	owner.solve("maxDelta(D)")
	    maxDelta = Integer.parseInt( owner.getCurSol("D").toString() )
    	owner.solve("amplif(D)")
	    amplif = Integer.parseInt( owner.getCurSol("D").toString() )
	    println("LimitDistance = $LimitDistance , maxDelta=$maxDelta, amplif =$amplif")	
	}
	
	override protected suspend fun elabData(data : String ){
		val Distance = Integer.parseInt( data )
		val delta    = Math.abs( Distance - LastDistance )
		println("   $name |  elabSonarData = $data LastDistance=$LastDistance, LimitDistance=$LimitDistance")
			detectObstacle( Distance )
		if( Distance > minDistance && Distance < maxDistance && delta >= maxDelta ){
			//detectObstacle( Distance )   //virtual robot IMPACTS => Distance always = 5
			LastDistance = Distance
			val m1 = MsgUtil.buildEvent(name, "sonarData", "sonarData($data)")
			//println("   ${name} |  using ${owner.name}  emit m1= $m1")
			 //emitLocalStreamEvent( "sonarData($Distance)" )  //PROPAGATE to the pipe  
			 owner.emit( m1 )  //data for the mind
			 val m2 = MsgUtil.buildEvent(name, "polar", "p(${Distance*amplif}, 90)")
			 owner.emit( m2 )  //data for a radar
 		}else{
			//println("   $name |  DISCARDS $Distance ")
		}				
	}
	
	//This operation can be delegated to the actor obstacleDetector in a PIPE
	suspend fun detectObstacle( Distance : Int ){
		if( Distance < LimitDistance ){
 			val m1 = MsgUtil.buildEvent(name, "obstacle", "obstacle($Distance)")
			println("   ${owner.name} |  sonardatafilter detectObstacle emit m1= $m1 ")
			emitLocalStreamEvent( "obstacle($Distance) " )  //PROPAGATE to the pipe
			owner.emit( m1 ) 
		}else{
			//println("   $name |  DISCARDS $Distance ")
		}		
	}
	
}