package it.unibo.qak.stream

import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope

class Filter(name:String, scope: CoroutineScope ) : ActorBasic( name, scope ){
    var filter : (Int) -> Boolean = { it -> true }       //no filter
    override suspend fun actorBody(msg : ApplMessage){
        println("   $name |  receives msg= $msg ")
        try{
            val v = Integer.parseInt( msg.msgContent() )
            if( filter(v)  )  emitLocalStreamEvent( msg )
        }catch(  e : Exception ){
            emitLocalStreamEvent( msg )
        }
    }

    fun setFilterFunction( ff : (Int) -> Boolean ){
        filter = ff
    }
}