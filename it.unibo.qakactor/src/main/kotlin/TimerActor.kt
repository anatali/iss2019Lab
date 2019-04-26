package it.unibo.kactor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerActor(name: String, scope: CoroutineScope, val ctx : QakContext,
                 val ev: String, val tout : Long) : ActorBasic(name,scope){

    init{
        println("TimerActor CREATED ${ev} time=$tout")
        this.context = ctx
        scope.launch{ autoMsg("start", "start") }
    }
    override suspend fun actorBody(msg : ApplMessage){
        println("TimerActor RECEIVES  ${msg} ")
        if( msg.msgId() == "start") {
            delay(tout)
            println("TimerActor EMITS  ${ev} ")
            this.emit(ev, ev)
            this.actor.close()
        }
    }
}