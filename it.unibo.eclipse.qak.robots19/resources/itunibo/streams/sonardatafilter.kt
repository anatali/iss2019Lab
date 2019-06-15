package itunibo.streams
import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import alice.tuprolog.Struct
import alice.tuprolog.Term

class sonardatafilter( name : String, val producerActor : ActorBasicFsm) : ActorBasic( name, producerActor.scope ) {
    private val LimitDistance  = 10
	private var LastDistance = 0
	private val minDistance  = 2
	private val maxDistance  = 50
	private val maxDelta     = 1
	private val amplif       = 6	//radar does D/3
//TODO: configure mindistance and maxdistcme on a sonarDataTHeory.pl
//	init{
//		subscribe( obstacleDetector("obsDetector", producerActor )  )
//		println("   $name |  subscribed the obsDetector")
//	}
    override suspend fun actorBody(msg: ApplMessage) {
        //println("   $name |  receives msg= $msg ${producerActor.name}")
//Event sonarRobot : sonar( DISTANCE ) FROM PLANT (sonarHCSR04SupportAsStream)
		val vStr     = (Term.createTerm( msg.msgContent() ) as Struct).getArg(0)
		val Distance = Integer.parseInt( vStr.toString() )
		val delta    = Math.abs( Distance - LastDistance )
		//println("   $name |  delta= $delta")
		if( Distance > minDistance && Distance < maxDistance && delta > maxDelta ){
			detectObstacle( Distance )
			LastDistance = Distance
			val m1 = MsgUtil.buildEvent(name, "sonarData", "sonarData($vStr)")
			//println("   ${name} |  using ${producerActor.name}  emit m1= $m1")
			emitLocalStreamEvent( m1 )  //propagate to the pipe
			val m2 = MsgUtil.buildEvent(name, "polar", "p(${Distance*amplif}, 90)")
			scope.launch{ producerActor.emit( m2 ) }	//data for a radar)
 		}else{
			//println("   $name |  DISCARDS $Distance ")
		}
	}
	//This operation can be delegated to the actor obstacleDetector
	fun detectObstacle( Distance : Int ){
		if( Distance < LimitDistance ){
 			val m1 = MsgUtil.buildEvent(name, "obstacle", "obstacle($Distance)")
			//println("   ${sourcePlayer.name} |  emit m1= $m1")
			scope.launch{ producerActor.emit( m1 ) }
		}else{
			//println("   $name |  DISCARDS $Distance ")
		}		
	}
}
