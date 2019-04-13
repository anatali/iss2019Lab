package it.unibo.qak.stream

import it.unibo.kactor.ApplMessage
import kotlinx.coroutines.CoroutineScope

class ConsumerShow(name:String, scope: CoroutineScope) : ObservableActor( name, scope ){

    override suspend fun actorBody(msg : ApplMessage){
        println("   ConsumerShow $name |  receives msg= $msg ")
        println()
     }


}