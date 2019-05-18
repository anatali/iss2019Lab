package itunibo.robot
import it.unibo.kactor.ActorBasic

object robotSupport{
	lateinit var robotKind : String
	
	fun create( actor: ActorBasic, robot : String ){
		robotKind = robot
		when( robotKind ){
			"virtual"    ->  { itunibo.robotVirtual.clientWenvObjTcp.initClientConn( actor, "localhost" ) }
			"realmbot"   ->  { itunibo.robotMbot.mbotSupport.create( actor, "COM6") }  //"/dev/ttyUSB0"   "COM6"
			//"realnano" ->    { it.unibo.robotRaspOnly }
			else -> println( "robot unknown" )
		}
	}
	
	fun move( cmd : String ){ //cmd = msg(M) M=w | a | s | d | h
		println("robotSupport move cmd=$cmd robotKind=$robotKind" )
		when( robotKind ){
			"virtual"  -> { itunibo.robotVirtual.clientWenvObjTcp.sendMsg(  cmd ) }	
			"realmbot" -> { itunibo.robotMbot.mbotSupport.move( cmd ) }
			else       -> println( "robot unknown" )
		}
		
	}
	
}