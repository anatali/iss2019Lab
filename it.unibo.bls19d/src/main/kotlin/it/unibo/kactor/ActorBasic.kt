package it.unibo.kactor

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.newSingleThreadContext

/*
    Implements an abstract actor able to receive an ApplMessage and to delegate
    its processing to the abstract method actorBody
 */
abstract class  ActorBasic( val name: String, val confined : Boolean = false ){

    protected val dispatcher = if( confined )
        newSingleThreadContext("ActorThread")
    else newFixedThreadPoolContext(4, "mypool")

    protected var count = 1;

    val  actor = GlobalScope.actor<ApplMessage>(dispatcher, 10 ) {
        for( msg in channel ) {
            //println("   ActorBasic $name |  msg= $msg "  )
            actorBody( msg )
        }
    }
    //To be defined by the application designer
    abstract suspend fun actorBody(msg : ApplMessage)

    fun getChannel() : SendChannel<ApplMessage> {
        return  actor
    }
    suspend fun autoMsg(  msg : ApplMessage) {
        actor.send( msg )
    }
    suspend fun autoMsg( msgId : String, msg : String) {
        actor.send( buildDispatch(msgId, msg, this.name) )
    }

    fun buildDispatch( msgId : String , content : String, dest: String ) : ApplMessage {
        return ApplMessage(msgId, "dispatch",
            this.name, dest, "$content", "" + count++)
    }

    fun forward( msgId : String, msg: String, destActor: ActorBasic) {
        GlobalScope.launch {
            destActor.getChannel().send(   buildDispatch(msgId, msg, destActor.name ) )
        }
    }

    fun forward( msgId : String, msg: String, destName: String) {
        println("TODO forward $msgId : $msg to $destName SEARCH IN SYS DESCR" )
        /*
        GlobalScope.launch {
            destActor.getChannel().send(   buildDispatch(msgId, msg, destName ) )
        }
        */
    }

}