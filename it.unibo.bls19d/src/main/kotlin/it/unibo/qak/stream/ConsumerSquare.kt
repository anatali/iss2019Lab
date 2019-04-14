package it.unibo.qak.stream

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MsgUtil
import it.unibo.qak.prodCons.DataItem
import kotlinx.coroutines.CoroutineScope

class ConsumerSquare(name:String, scope: CoroutineScope) : ActorBasic( name, scope ){

    override suspend fun actorBody(msg : ApplMessage){
        println("   $name |  receives msg= $msg ")
        val vs = msg.msgContent()
        if( vs.contains("completed")){
            emitLocalStreamEvent( msg )
            return
        }
        var v = Integer.parseInt( msg.msgContent() )
        val m = MsgUtil.buildEvent(name, DataItem.id,"${v*v}" )
        emitLocalStreamEvent( m )
    }
}