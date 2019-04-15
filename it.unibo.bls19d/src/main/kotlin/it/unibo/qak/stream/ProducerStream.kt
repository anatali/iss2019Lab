package it.unibo.qak.stream

import it.unibo.kactor.*
import it.unibo.qak.prodCons.DataItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

class ProducerStream( name:String, scope:CoroutineScope ) : ActorBasic(name,scope){
var max = 10
    override suspend fun actorBody(msg : ApplMessage){
        //if( msg.msgSender() != name ) println("   ProducerStream $name |  receives msg= $msg ")
        when( msg.msgId()){
            "start"     -> {
                max = Integer.parseInt( msg.msgContent() )
                produce(1)
            }
            DataItem.id -> {
                if( msg.msgContent().contains("completed")) return
                val v = Integer.parseInt( msg.msgContent() )
                if( v < max ) produce(v+1) else dataCompleted() }
            //else ->  println("   ProducerStream $name |  receives msg= $msg ")
        }
    }

     suspend fun produce(v:Int){
            //if( subscribers.size > 0) {  //NO subscriber => no production
                val msg = MsgUtil.buildEvent(name, DataItem.id, "${v}")
                //println("   ProducerStream $name | PRODUCES ${v} ")
                emitLocalStreamEvent(msg)  //local
                emit(msg.msgId(), msg.msgContent()) //Used as an auto-stimulation
             //}else println("   ProducerStream $name | NO OBSERVERS ")
            delay(500)
    }

    suspend fun dataCompleted(){
        val d   = DataItem("completed")
        val msg = MsgUtil.buildEvent(name, d.id,d.item  )
        emitLocalStreamEvent( msg )
        emit(msg.msgId(), msg.msgContent())
        println("   ProducerStream $name | COMPLETED ")
    }

}