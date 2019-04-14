package it.unibo.qak.producer

import it.unibo.kactor.*
import it.unibo.qak.prodCons.DataItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

class Producer( name : String, scope: CoroutineScope) : ActorBasic( name, scope ) {
    var max = 10
    override suspend fun actorBody(msg : ApplMessage){
        //println("   Producer $name |  receives msg= $msg ")
        when( msg.msgId()){
            "local_start"     -> {
                max = Integer.parseInt( msg.msgContent() )
                produce(1)
            }
            "local_${DataItem.id}"  -> {
                if( msg.msgContent().contains("completed")) return
                val v = Integer.parseInt( msg.msgContent() )
                if( v < max ) produce(v+1) else dataCompleted() }
            //else ->  println("   Producer $name |  receives msg= $msg ")
        }
    }

    suspend fun produce(v:Int){
        //if( subscribers.size > 0) {  //NO subscriber => no production
        val msg = MsgUtil.buildEvent(name, DataItem.id, "${v}")
        //println("   Producer $name | PRODUCES ${v} ")
        /*
        PRODUCER AS OBSERVABLE
         */
        emitLocalStreamEvent(msg)
        /*
        PRODUCER AS EVENT EMITTER
         */
        emit("local_${msg.msgId()}", msg.msgContent()) //Used as an auto-stimulation
        emit(msg.msgId(), msg.msgContent()) //Used to propagate info (also to the logger!)
        //}else println("   Producer $name | NO OBSERVERS ")
        delay(200)
    }

    suspend fun dataCompleted(){
        val d   = DataItem("completed")
        val msg = MsgUtil.buildEvent(name, d.id,d.item  )
        emitLocalStreamEvent( msg )
        emit( "local_${msg.msgId()}", msg.msgContent())
        println("   Producer $name | COMPLETED ")
    }

}
