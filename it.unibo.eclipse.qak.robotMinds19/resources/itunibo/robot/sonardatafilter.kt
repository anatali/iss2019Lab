package itunibo.robotMbot
import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import alice.tuprolog.Struct
import alice.tuprolog.Term

class sonardatafilter( name : String, val producerActor : ActorBasicFsm) : ActorBasic( name, producerActor.scope ) {
    private var LimitDistance  = 12
	private var LastDistance = 0
	private var minDistance  = 2
	private var maxDistance  = 50
	private var maxDelta     = 1
	private var amplif       = 6	//radar does D/3
	
	public var modeReact   = "oop"
 	init{
		//subscribe( obstacleDetector("obsDetector", producerActor )  )
		println("   $name | producerActor= ${producerActor.name}")
    	producerActor.solve("limitDistance(D)")
	    LimitDistance = Integer.parseInt( producerActor.getCurSol("D").toString() )
    	producerActor.solve("minDistance(D)")
	    minDistance = Integer.parseInt( producerActor.getCurSol("D").toString() )
    	producerActor.solve("maxDistance(D)")
	    maxDistance = Integer.parseInt( producerActor.getCurSol("D").toString() )
    	producerActor.solve("maxDelta(D)")
	    maxDelta = Integer.parseInt( producerActor.getCurSol("D").toString() )
    	producerActor.solve("amplif(D)")
	    amplif = Integer.parseInt( producerActor.getCurSol("D").toString() )
	    println("LimitDistance = $LimitDistance , amplif =$amplif")
	
    	producerActor.solve("modeReact(M)")
	    modeReact =  producerActor.getCurSol("M").toString() 
	    println("modeReact = $modeReact  ")
	}
	
    override suspend fun actorBody(msg: ApplMessage) {
        //println("   $name |  receives msg= $msg from ${producerActor.name}")
//Event sonarRobot : sonar( DISTANCE ) FROM PLANT (sonarHCSR04SupportAsStream or virtual robot)
		val vStr     = (Term.createTerm( msg.msgContent() ) as Struct).getArg(0)
		elabSonarData( vStr.toString() )
	}
	
	/*
 	ENTRY IN OOP style (see ) 
    */
	fun elabSonarData( vStr: String ){
		val Distance = Integer.parseInt( vStr )
		val delta    = Math.abs( Distance - LastDistance )
		println("   $name |  elabSonarData = $vStr LastDistance=$LastDistance, LimitDistance=$LimitDistance")
		detectObstacle( Distance )		//TODO
		if( Distance > minDistance && Distance < maxDistance && delta >= maxDelta ){
			//detectObstacle( Distance )
			LastDistance = Distance
			val m1 = MsgUtil.buildEvent(name, "sonarData", "sonarData($vStr)")
			//println("   ${name} |  using ${producerActor.name}  emit m1= $m1")
			//emitLocalStreamEvent( m1 )  //propagate to the pipe (when using obsDetector)
			scope.launch{ producerActor.emit( m1 ) }	//data for the mind
			val m2 = MsgUtil.buildEvent(name, "polar", "p(${Distance*amplif}, 90)")
			scope.launch{ producerActor.emit( m2 ) }	//data for a radar
 		}else{
			//println("   $name |  DISCARDS $Distance ")
		}		
	}
	
	//This operation can be delegated to the actor obstacleDetector in a PIPE
	fun detectObstacle( Distance : Int ){
		if( Distance < LimitDistance ){
 			val m1 = MsgUtil.buildEvent(name, "obstacle", "obstacle($Distance)")
			println("   ${producerActor.name} |  sonardatafilter detectObstacle emit m1= $m1 ")
			scope.launch{ producerActor.emit( m1 ) }
		}else{
			//println("   $name |  DISCARDS $Distance ")
		}		
	}
}
