package itunibo.robot

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import alice.tuprolog.Struct
import alice.tuprolog.Term
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.CoroutineScope

abstract class ApplActorDataStream(name : String, scope : CoroutineScope = GlobalScope ) : ActorBasic(name, scope){
 	//init{	}
	
    override suspend fun actorBody(msg: ApplMessage) {
 		val vStr  = (Term.createTerm( msg.msgContent() ) as Struct).getArg(0).toString()
        //println("   $name |  handles msg= $msg  vStr=$vStr")
		elabData( vStr )
	}
	
	suspend fun emitLocalStreamEvent( msg : String){
		//println("   $name |  emitLocalStreamEvent msg= $msg  ")
		emitLocalStreamEvent( MsgUtil.buildEvent(name, msg,"ev($msg)") )
	}
	
	abstract protected suspend fun elabData(data : String );

}