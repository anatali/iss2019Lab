    package it.unibo.kactor

import alice.tuprolog.Prolog
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor

/*
    Implements an abstract actor able to receive an ApplMessage and
    to delegate its processing to the abstract method actorBody
 */

abstract class  ActorBasic(val name:         String,
                           val scope:        CoroutineScope = GlobalScope,
                           val confined :    Boolean = false,
                           val ioBound :     Boolean = false,
                           val channelSize : Int = 50
                        ) {
    //val cpus = Runtime.getRuntime().availableProcessors();
    var context : QakContext? = null  //to be injected
    val pengine = Prolog()      //USED FOR LOCAL KB
    val mqtt    = MqttUtils()
    var mqttConnected = false
    protected var count = 1;

     protected val dispatcher =
        if( confined ) sysUtil.singleThreadContext
        else  if( ioBound ) sysUtil.ioBoundThreadContext
              else sysUtil.cpusThreadContext


    val actor = scope.actor<ApplMessage>(
            dispatcher, capacity=channelSize ) {
        //println("   ActorBasic $name |  RUNNING IN $dispatcher"  )
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
        //println("       ActorBasic $name | forward $msgId:$msg to ${destActor.name} in ${sysUtil.curThread() }")
        destActor.actor.send(
            MsgUtil.buildDispatch(name, msgId, msg, destActor.name ) )
     }

    suspend fun forward( msgId : String, msg: String, destName: String) {
        //println("       ActorBasic $name |  forward $msgId to $destName -  ${sysUtil.curThread()}")
        if( context == null ){
            //println("WARNING forward : there is no QakContext")
            return
        }
        val actor = context!!.hasActor(destName)
        //println("forward $msgId : $msg to $destName IN context=${context.name} actor=$actor" )
         if( actor is ActorBasic   ) {//local
            forward( msgId, msg, actor)
        }else{ //remote
             val ctx   = sysUtil.getActorContext(destName)
             val m      = MsgUtil.buildDispatch(name,msgId, msg, destName)
             //println("       ActorBasic $name | forward mqttAddr= ${ctx!!.mqttAddr}")
             if( ctx!!.mqttAddr.length > 0  ) {
                 //The producer should be connected to the MQTT broker
                 if( ! mqttConnected ){
                     mqtt.connect(name, ctx!!.mqttAddr)
                     mqttConnected = true
                 }
                 mqtt.sendMsg(m, "unibo/qak/$destName")
                 return
             }
             val proxy = context!!.proxyMap.get(ctx.name)
             println("       ActorBasic $name | forward $msgId : $msg to external $destName IN context=${ctx} " )
             //WARNING: destName must be the original and not the proxy
            if( proxy is ActorBasic )
                proxy.actor.send( m )
            else println("       ActorBasic $name | proxy of $ctx is null ")
          }
    }//forward


    suspend fun emit( msgId : String, msg : String) {
        val event = MsgUtil.buildEvent(name,msgId, msg)
         if( context == null ){
            //println("WARNING emit: there is no QakContext")
            return
        }
        //PROPAGATE TO LOCAL ACTORS
        context!!.actorMap.forEach{
            //val destName  = it.key
            val destActor = it.value
            destActor.actor.send( event )
        }
         //PROPAGATE TO REMOTE ACTORS
        if( msgId.startsWith("local")) return       //local_ => no propagation

        sysUtil.ctxsMap.forEach{
            val ctxName  = it.key
            val ctx      = it.value
            //println("       ActorBasic $name | ${ctxName} emit mqttAddr= ${ctx!!.mqttAddr} mqttConnected=$mqttConnected")
            if( ctx!!.mqttAddr.length > 0) {
                if( ! mqttConnected ){
                    mqtt.connect(name, ctx!!.mqttAddr)
                    mqttConnected = true
                }
                mqtt.sendMsg(event, "unibo/qak/events")
                return
            }
            val proxy  = context!!.proxyMap.get(ctxName)
            if( proxy is ActorBasic ){
                //println("       ActorBasic $name | emit $event  towards $ctxName " )
                proxy.actor.send( event )
            }
            //else{ println("       ActorBasic $name | emit in ${context.name} : proxy  of $ctxName is null ") }
        }
    }

/*
--------------------------------------------
MQTT
--------------------------------------------
 */
    fun checkMqtt(){
        if( context!!.mqttAddr.length > 0  ){
            mqtt.connect(name,context!!.mqttAddr)
            mqttConnected = true
            mqtt.subscribe(this, "unibo/qak/$name")
            mqtt.subscribe(this, "unibo/qak/events")
        }
    }

}