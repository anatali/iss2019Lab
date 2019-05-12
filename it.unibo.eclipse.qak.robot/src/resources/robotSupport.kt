import it.unibo.kactor.ActorBasic

object robotSupport{
	lateinit var robotKind : String
	
	fun create( actor: ActorBasic, robot : String ){
		robotKind = robot
		when( robotKind ){
			"virtual"  -> { resources.clientWenvObjTcp.initClientConn( actor, "localhost" ) }
			"realmbot" ->  {  }
			"relacustom" -> {}
			else -> println( "robot unknown" )
		}
	}
	
	fun move( cmd : String ){
		when( robotKind ){
			"virtual"  -> { resources.clientWenvObjTcp.sendMsg(  cmd ) }
			else -> println( "robot unknown" )
		}
		
	}
	
}