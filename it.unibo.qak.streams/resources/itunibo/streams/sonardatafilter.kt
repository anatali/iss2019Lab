package itunibo.streams
import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import alice.tuprolog.Struct
import alice.tuprolog.Term


class sonardatafilter( name : String, val producerActor : ActorBasic) : ActorBasic( name, producerActor.scope ) {
	private var LastDistance = 0
	private val minDistance  = 1
	private val maxDistance  = 50
//TODO: configure mindistance and maxdistcme on a sonarDataTHeory.pl
	
    override suspend fun actorBody(msg: ApplMessage) {
        //println("   $name |  receives msg= $msg $producerActor")
//Event sonarRobot : sonar( DISTANCE ) FROM PLANT (sonarHCSR04SupportAsStream)
		val vStr     = (Term.createTerm( msg.msgContent() ) as Struct).getArg(0)
		val Distance = Integer.parseInt( vStr.toString() )
		val delta    = Math.abs( Distance - LastDistance )
		//println("   $name |  delta= $delta")
		if( delta > minDistance && delta < maxDistance ){
			LastDistance = Distance
			val m1 = MsgUtil.buildEvent(name, "sonarData", "sonarData($vStr)")
			//println("   ${producerActor.name} |  emit m1= $m1")
			scope.launch{ producerActor.emit( m1 ) }
		}else{
			//println("   $name |  DISCARDS $Distance ")
		}
	}  
}
