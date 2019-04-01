package it.unibo.kactor

import alice.tuprolog.Prolog
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor

/*
    Implements an abstract actor able to receive an ApplMessage and
    to delegate its processing to the abstract method actorBody
 */

abstract class  ActorBasic(val name: String,
                           val ioBound : Boolean = false,
                           val channelSize : Int = 5,
                           val confined : Boolean = false ) {
    val cpus = Runtime.getRuntime().availableProcessors();
    lateinit var context : QakContext  //to be injected
    val pengine = Prolog()      //USED FOR LOCAL KB

    protected val dispatcher =
        if( confined )
        newSingleThreadContext("qaksingle")
        else
            if( ioBound )
                newFixedThreadPoolContext(64, "qakiopool")
            else newFixedThreadPoolContext(cpus, "qakpool")

    protected var count = 1;

    val  actor = GlobalScope.actor<ApplMessage>(dispatcher, capacity=channelSize ) {
        for( msg in channel ) {
            //println("   ActorBasic $name |  msg= $msg "  )
            actorBody( msg )
        }
    }
    //To be defined by the application designer
    abstract suspend fun actorBody(msg : ApplMessage)

    //fun setContext( ctx: QakContext ) //built-in


/*
--------------------------------------------
Messaging
--------------------------------------------
 */
    suspend fun autoMsg(  msg : ApplMessage) {
    //println("ActorBasic $name | autoMsg $msg ")
     actor.send( msg )
    }
    suspend fun autoMsg( msgId : String, msg : String) {
        actor.send( MsgUtil.buildDispatch(name, msgId, msg, this.name) )
    }
    suspend fun forward( msgId : String, msg: String, destActor: ActorBasic) {
        println("       ActorBasic $name | forward $msgId:$msg to ${destActor.name} in ${sysUtil.curThread() }")
        destActor.actor.send(
            MsgUtil.buildDispatch(name, msgId, msg, destActor.name ) )
     }

    suspend fun forward( msgId : String, msg: String, destName: String) {
        //println("       ActorBasic $name |  forward $msgId to $destName -  ${sysUtil.curThread()}")
        val actor = context!!.hasActor(destName)
        //println("forward $msgId : $msg to $destName IN context=${context.name} actor=$actor" )
        if( actor is ActorBasic   ) {//local
            forward( msgId, msg, actor)
        }else{ //remote
             val ctx   = sysUtil.getActorContext(destName)
             val proxy = QakContext.proxyMap.get(ctx)
             //println("forward $msgId : $msg to $destName IN context=${ctx} proxy=$proxy" )
             //WARNING: destName must be the original and not the proxy
            if( proxy is ActorBasic )
                proxy!!.actor.send(MsgUtil.buildDispatch(name,msgId, msg, destName))
            else println("       ActorBasic $name | proxy of $ctx is null ")
          }
    }
}