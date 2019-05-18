package resources
import it.unibo.kactor.ActorBasic

object robotSupport{
	lateinit var robotKind : String
	
	fun create( actor: ActorBasic, robot : String ){
		robotKind = robot
		when( robotKind ){
			"virtual"    ->  { resources.clientWenvObjTcp.initClientConn( actor, "localhost" ) }
			"realmbot"   ->  { mbotSupport.create( actor, "COM6") }  //"/dev/ttyUSB0"   "COM6"
			"realnano"   ->  {}
			else -> println( "robotSupport-create: robot unknown" )
		}
	}
	
	fun move( cmd : String ){ //cmd = msg(M) M=w | a | s | d | h
		//println("robotSupport move cmd=$cmd robotKind=$robotKind" )
		when( robotKind ){
			"virtual"  -> { resources.clientWenvObjTcp.sendMsg(  cmd ) }	
			"realmbot" -> { mbotSupport.move( cmd ) }
			"realnano" ->  {}
			else       -> println( "robotSupport-move: robot unknown" )
		}
		
	}
	
}