package it.unibo.qak.stream

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import kotlinx.coroutines.CoroutineScope

abstract class ObservableActor(name:String, scope: CoroutineScope) : ActorBasic(name, scope){
    /*
    protected val subscribers = mutableListOf<ActorBasic>()

    fun subscribe( a : ObservableActor) : ObservableActor {
        subscribers.add(a)
        return a
    }

    open protected suspend fun emitLocalStreamEvent(v: ApplMessage ){
        subscribers.forEach { it.actor.send(v) }
    }
    */

}