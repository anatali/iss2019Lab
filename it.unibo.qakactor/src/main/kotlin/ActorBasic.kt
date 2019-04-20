package it.unibo.kactor

import alice.tuprolog.Prolog
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.util.NoSuchElementException


    /*
        Implements an abstract actor able to receive an ApplMessage and
        to delegate its processing to the abstract method actorBody
     */

abstract class  ActorBasic(val name:         String,
                           val scope:        CoroutineScope = GlobalScope,
                           val confined :    Boolean = false,
                           val ioBound :     Boolean = false,
                           val channelSize : Int = 50
                        ) : MqttCallback {
    //val cpus = Runtime.getRuntime().availableProcessors();
    var context : QakContext? = null  //to be injected
    val pengine = Prolog()      //USED FOR LOCAL KB
    val NoMsg = MsgUtil.buildEvent(name, "noMsg", "noMsg")

    val mqtt    = MqttUtils()
    protected val subscribers = mutableListOf<ActorBasic>()
    var mqttConnected = false
    protected var count = 1;

     protected val dispatcher =
        if( confined ) sysUtil.singleThreadContext
        else  if( ioBound ) sysUtil.ioBoundThreadContext
              else sysUtil.cpusThreadContext


    val actor = scope.actor<ApplMessage>(
            dispatcher, capacity=channelSize ) {
        //println("ActorBasic $name |  RUNNING IN $dispatcher"  )
        for( msg in channel ) {
            //println("ActorBasic $name |  msg= $msg "  )
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
    suspend open fun autoMsg(  msg : ApplMessage) {
     //println("ActorBasic $name | autoMsg $msg actor=${actor}")
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
            println("WARNING forward : there is no QakContext")
            return
        }
        val actor = context!!.hasActor(destName)
        //println("forward $msgId : $msg to $destName IN context=${context!!.name} actor=$actor" )
         if( actor is ActorBasic   ) {//local
            forward( msgId, msg, actor)
        }else{ //remote
             val ctx   = sysUtil.getActorContext(destName)
             //println("       ActorBasic $name | forward ctx= ${ctx}")
             val m = MsgUtil.buildDispatch(name,msgId, msg, destName)
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
             //println("       ActorBasic $name | forward $msgId : $msg to external $destName IN context=${ctx} " )
             //WARNING: destName must be the original and not the proxy
            if( proxy is ActorBasic )
                proxy.actor.send( m )
            else println("       ActorBasic $name | proxy of $ctx is null ")
          }
    }//forward

    suspend fun emit( event : ApplMessage ) {
        if( context == null ){
            //println("WARNING emit: there is no QakContext")
            this.actor.send(event)  //AUTOMSG
            return
        }
        //PROPAGATE TO LOCAL ACTORS
        context!!.actorMap.forEach{
            //val destName  = it.key
            val destActor = it.value
            destActor.actor.send( event )
        }
        //PROPAGATE TO REMOTE ACTORS
        if( event.msgId().startsWith("local")) return       //local_ => no propagation

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

    suspend fun emit( msgId : String, msg : String) {
        val event = MsgUtil.buildEvent(name,msgId, msg)
        emit( event )
    }

/*
 --------------------------------------------
 OBSERVABLE
 --------------------------------------------
*/
    fun subscribe( a : ActorBasic) : ActorBasic {
        subscribers.add(a)
        return a
    }
    fun unsubscribe( a : ActorBasic) {
        subscribers.remove(a)
    }
    protected suspend fun emitLocalStreamEvent(v: ApplMessage ){
        subscribers.forEach { it.actor.send(v) }
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

    override fun messageArrived(topic: String, msg: MqttMessage) {
        //println("       ActorBasic $name |  messageArrived on "+ topic + ": "+msg.toString());
        val m = ApplMessage( msg.toString() )
        this.scope.launch{ actor.send( m ) }

    }
    override fun connectionLost(cause: Throwable?) {
        println("       ActorBasic $name | connectionLost $cause " )
    }
    override fun deliveryComplete(token: IMqttDeliveryToken?) {
//		println("       ActorBasic $name |  deliveryComplete token= "+ token );
    }

/*
--------------------------------------------
machineExec
--------------------------------------------
 */
    fun machineExec(cmd: String) : Process {
        try {
            return sysUtil.runtimeEnvironment.exec(cmd)
        } catch (e: Exception) {
            println("       ActorBasic $name | machineExec ERROR $e ")
            throw e
        }
    }



}