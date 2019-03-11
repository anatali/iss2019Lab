package it.unibo.kactor

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.newFixedThreadPoolContext

/*
    Implements an abstract actor able to receive an ApplMessage and to delegate
    its processing to the abstract method actorBody
 */
abstract class  ActorBasic( val name: String ){
    protected val dispatcher = newFixedThreadPoolContext(2, "mypool")

    @kotlinx.coroutines.ObsoleteCoroutinesApi
    val  actor = GlobalScope.actor<ApplMessage>(dispatcher, 3 ) {
        for( msg in channel ) {
            println("   ActorBasic $name |  msg= $msg "  )
            actorBody( msg )
        }
    }
    //To be defined by the application designer
    abstract suspend fun actorBody(msg : ApplMessage)

    fun getChannel() : SendChannel<ApplMessage> {
        return  actor
    }
    suspend fun autoMsg( msg : ApplMessage) {
        actor.send( msg )
    }
}