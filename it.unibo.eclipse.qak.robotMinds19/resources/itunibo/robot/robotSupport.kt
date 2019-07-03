package itunibo.robot
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ActorBasicFsm
 
object robotSupport{
	lateinit var robotKind : String
	
	fun create( actor: ActorBasicFsm, robot : String, port: String, filter:ApplActorDataStream?  ){
		robotKind = robot
		println( "CREATE ROBOT SUPPORT for $robotKind" )
		when( robotKind ){
			"virtual"    ->  { itunibo.robotVirtual.clientWenvObjTcp.initClientConn( actor, filter, "localhost") }
			"realmbot"   ->  { itunibo.robotMbot.mbotSupport.create( actor, port, filter ) }  //port="/dev/ttyUSB0"   "COM6"
			"realnano" ->    { it.unibo.robotRaspOnly.nanoSupport.create(actor, true ) }
			else -> println( "robot unknown" )
		}
	}
	
	fun move( cmd : String ){ //cmd = msg(M) M=w | a | s | d | h
		//println("robotSupport move cmd=$cmd robotKind=$robotKind" )
		when( robotKind ){
			"virtual"  -> { itunibo.robotVirtual.clientWenvObjTcp.sendMsg(  cmd ) }	
			"realmbot" -> { itunibo.robotMbot.mbotSupport.move( cmd ) }
			"realnano" -> { it.unibo.robotRaspOnly.nanoSupport.move( cmd ) }
			else       -> println( "robot unknown" )
		}
		
	}
	
}