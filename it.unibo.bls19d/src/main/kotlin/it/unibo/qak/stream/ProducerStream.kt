package it.unibo.qak.stream

import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MsgUtil
import it.unibo.qak.prodCons.DataItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

class ProducerStream( name:String, scope:CoroutineScope ) : ObservableActor(name,scope){
    var v = 1

    override suspend fun actorBody(msg : ApplMessage){
        println("   ProducerStream $name |  receives msg= $msg ")
        when( msg.msgId()){
            "start" -> produce()
            //else ->  println("   ProducerStream $name |  receives msg= $msg ")
        }
    }

     suspend fun produce(){
        //for( i in 1..10 ){
            if( subscribers.size > 0){
                  //val d = DataItem("data(${v++})")
                val d   = DataItem("${v}")
                val msg = MsgUtil.buildEvent(name, d.id,d.item )
                //println("   ProducerStream $name | PRODUCES ${v} ")
                emitLocalStreamEvent( msg )  //local
                emit(msg.msgId(), msg.msgContent()) //remote ALSO TO ITSELF
                v++
             }
            else{
                println("   ProducerStream $name | NO OBSERVERS ")
            }
            delay(500)
       // }
/*
        //NO MORE DATA
         println("   ProducerStream $name | COMPLETED ")
         val d = DataItem("completed")
        val msg = MsgUtil.buildEvent(name, d.id,d.item  )
        emitLocalStreamEvent( msg )
        emit(msg.msgId(), msg.msgContent()) //remote
*/
    }



}