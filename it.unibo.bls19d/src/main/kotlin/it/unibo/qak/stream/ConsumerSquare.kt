package it.unibo.qak.stream

import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.CoroutineScope

class ConsumerSquare(name:String, scope: CoroutineScope) : ObservableActor( name, scope ){

    override suspend fun actorBody(msg : ApplMessage){
        println("   ConsumerSquare $name |  receives msg= $msg ")
        val vs = msg.msgContent()
        if( vs.contains("completed")){
            val m = MsgUtil.buildEvent(name, "","completed" )
            emitLocalEvent( m )
            return
        }
        var v = Integer.parseInt( msg.msgContent() )
        v = v * v
        val m = MsgUtil.buildEvent(name, "","$v" )
        emitLocalEvent( m )
    }


}