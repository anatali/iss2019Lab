package it.unibo.kactor

import alice.tuprolog.Prolog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.newSingleThreadContext

/*
    Implements an abstract actor able to receive an ApplMessage and
    to delegate its processing to the abstract method actorBody
 */

abstract class  ActorBasic( val name: String,
                            val channelSize : Int = 5,
                            val confined : Boolean = false ) {
    val cpus = Runtime.getRuntime().availableProcessors();
    lateinit var context : QakContext  //to be injected
    val pengine = Prolog()      //USED FOR LOCAL KB

    protected val dispatcher = if( confined )
        newSingleThreadContext("qaksingle")
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
        println("ActorBasic $name | autoMsg $msg ")
     actor.send( msg )
    }
    suspend fun autoMsg( msgId : String, msg : String) {
        actor.send( MsgUtil.buildDispatch(name, msgId, msg, this.name) )
    }
    suspend fun forward( msgId : String, msg: String, destActor: ActorBasic) {
        println("ActorBasic $name |  forward local $msgId:$msg to ${destActor.name} in ${sysUtil.curThread() }")
        destActor.actor.send(
            MsgUtil.buildDispatch(name, msgId, msg, destActor.name ) )
     }

    suspend fun forward( msgId : String, msg: String, destName: String) {
        println("ActorBasic $name |  forward  ${sysUtil.curThread()}")
        println("forward $msgId : $msg to $destName SEARCH IN context=$context" )
        val actor = context!!.hasActor(destName)
        if( actor is ActorBasic   ) {//local
            forward( msgId, msg , actor)
            //actor.getChannel().send(MsgUtil.buildDispatch(name,msgId, msg, destName ) )
          }else{ //remote
             val ctx = sysUtil.getActorContext(destName)
             val proxy = context!!.proxyMap.get(ctx)
             forward( msgId, msg , proxy as ActorBasic)
             //proxy!!.getChannel().send(MsgUtil.buildDispatch(name,msgId, msg, destName))
          }
    }
}