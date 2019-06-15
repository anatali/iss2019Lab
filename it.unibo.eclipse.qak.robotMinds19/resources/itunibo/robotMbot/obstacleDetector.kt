package itunibo.robotMbot
import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import alice.tuprolog.Struct
import alice.tuprolog.Term

class obstacleDetector( name : String,
		val LimitDistance : Int, val sourcePlayer : ActorBasicFsm) : ActorBasic( name, sourcePlayer.scope ) {
 	init{
		 println("   $name |  CREATED")
	}
    override suspend fun actorBody(msg: ApplMessage) {
        //println("   $name |  receives msg= $msg $sourcePlayer")
//Event sonarData : sonarData( DISTANCE ) FROM pipe
		val vStr     = (Term.createTerm( msg.msgContent() ) as Struct).getArg(0)
		val Distance = Integer.parseInt( vStr.toString() )
 		println("   $name |  Distance= $Distance")
		if( Distance < LimitDistance ){
 			val m1 = MsgUtil.buildEvent(name, "obstacle", "obstacle($vStr)")
			//println("   ${sourcePlayer.name} |  emit m1= $m1")
			scope.launch{ sourcePlayer.emit( m1 ) }
		}else{
			//println("   $name |  DISCARDS $Distance ")
		}
	}  
}
