package it.unibo.qak.stream

import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MsgUtil
import it.unibo.qak.prodCons.DataItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

class ProducerStream( name:String, scope:CoroutineScope ) : ObservableActor(name,scope){
    var v = 1

    override suspend fun actorBody(msg : ApplMessage){
        if( msg.msgSender() != name )
            println("   ProducerStream $name |  receives msg= $msg ")
        when( msg.msgId()){
            "start"     -> produce()
            DataItem.id ->{
                if( msg.msgContent().contains("completed")) return
                if( v < 11 ) produce() else dataCompleted() }
            //else ->  println("   ProducerStream $name |  receives msg= $msg ")
        }
    }

     suspend fun produce(){
            //if( subscribers.size > 0) {  //NO subscriber => no production
                val d = DataItem("${v++}")
                val msg = MsgUtil.buildEvent(name, d.id, d.item)
                //println("   ProducerStream $name | PRODUCES ${v-1} ")
                emitLocalStreamEvent(msg)  //local
                emit(msg.msgId(), msg.msgContent()) //ALSO TO ITSELF (automesg)
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