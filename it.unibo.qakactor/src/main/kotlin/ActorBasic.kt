package it.unibo.kactor

import alice.tuprolog.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage


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
    var resVar  : String ="fail"      // see solve
    val pengine     = Prolog()      //USED FOR LOCAL KB
    val NoMsg       = MsgUtil.buildEvent(name, "local_noMsg", "noMsg")

    val mqtt    = MqttUtils()
    protected val subscribers = mutableListOf<ActorBasic>()
    var mqttConnected = false
    protected var count = 1;

    protected lateinit var currentSolution : SolveInfo
    protected lateinit var currentProcess  : Process

    private var timeAtStart: Long = 0

     protected val dispatcher =
        if( confined ) sysUtil.singleThreadContext
        else  if( ioBound ) sysUtil.ioBoundThreadContext
              else sysUtil.cpusThreadContext


    val actor = scope.actor<ApplMessage>(
            dispatcher, capacity=channelSize ) {
        //println("ActorBasic $name |  RUNNING IN $dispatcher"  )
        for( msg in channel ) {
            //println("ActorBasic $name |  msg= $msg "  )
            if( msg.msgContent() == "stopTheActor") {
                channel.close()
            }
            else{
                actorBody( msg )
            }
        }
    }
    //To be defined by the application designer
    abstract suspend fun actorBody(msg : ApplMessage)

    //fun setContext( ctx: QakContext ) //built-in
    fun terminate(){
        context!!.actorMap.remove(  name )
        actor.close()
    }

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
            println("WARNING forward : there is no QakContext.  ")  //Juky 2019
            return
        }
        val actor = context!!.hasActor(destName)
        //println("forward $msgId : $msg to $destName IN context=${context!!.name} actor=$actor" )
         if( actor is ActorBasic   ) {//local
            forward( msgId, msg, actor)
        }else{ //remote
             val ctx   = sysUtil.getActorContext(destName)
             if( ctx == null ) {
                 //println(" ActorBasic $name | context of $destName UNKNOWN - sendind via mqtt=$mqtt") //FOR reply
                 val m = MsgUtil.buildDispatch(name, msgId, msg, destName)       //JUNE2019
                 mqtt.sendMsg(m, "unibo/qak/$destName")
                 return
             }
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

    //var mqttPropagated = false;

    suspend fun emit( event : ApplMessage ) {
        //println("       ActorBasic $name | emit ${event.msgId()}  STARTS")
        if( context == null ){
            println("      ActorBasic $name | WARNING emit: actor has no QakContext. ")
             this.actor.send(event)  //AUTOMSG
             return
        }
        //PROPAGATE TO LOCAL ACTORS
        if( context!!.mqttAddr.length == 0  //There is NO MQTT for this context
            ||  //the event is local
            event.msgId().startsWith("local")  ) {
            context!!.actorMap.forEach {
                val destActor = it.value
                //println("      ActorBasic $name | PROPAGATE ${event.msgId()} locally to  ${destActor.name} ")
                destActor.actor.send(event)
            }
        }
        //PROPAGATE TO REMOTE ACTORS
        if( event.msgId().startsWith("local")) return       //local_ => no propagation
        //println("       ActorBasic $name | ctxsMap SIZE = ${sysUtil.ctxsMap.size}")
        //mqttPropagated = false;
        sysUtil.ctxsMap.forEach{
            val ctxName  = it.key
            val ctx      = it.value
            //println("       ActorBasic $name | ${context!!.name } emit ${event.msgId()} to ${ctxName}  mqttAddr= ${ctx!!.mqttAddr} ")
            val proxy  = context!!.proxyMap.get(ctxName)
            if( proxy is ActorBasic ){
                //println("       ActorBasic $name | emit ${event}  towards $ctxName " )
                proxy.actor.send( event )
            }else
            if( ctx!!.mqttAddr.length > 0) {    //the context works under MQTT
                if( ! mqttConnected ){
                    mqtt.connect(name, ctx!!.mqttAddr)
                    mqttConnected = true
                }
                //if( ctxName != context!!.name && ! mqttPropagated) { //avoid to send to itself again
                //if( ! mqttPropagated ) { //avoid to send more times
                    //println("       ActorBasic $name | emit MQTT ${event} while looking at $ctxName " )
                    mqtt.sendMsg(event, "unibo/qak/events")
                    //mqttPropagated = true
                    //return  //NO, since we must look at the other contexts BUT JUST ONE
                //}
            }
            //else{ println("       ActorBasic $name | emit in ${context.name} : proxy  of $ctxName is null ") }
        }
        //println("       ActorBasic $name | emit ${event.msgId()}  ENDS")
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
    fun subscribeLocalActor( actorName : String) : ActorBasic {
        val a = sysUtil.getActor(actorName)
        if( a != null  ){ subscribers.add(a); return a}
        else{ println("WARNING: actor $actorName not found" );
            throw Exception("actor $actorName not found in the current context")
        }
    }
    fun unsubscribe( a : ActorBasic) {
        subscribers.remove(a)
    }
    suspend fun emitLocalStreamEvent(ev: String, evc: String ){
        emitLocalStreamEvent( MsgUtil.buildEvent( name, ev, evc) )
    }
    suspend fun emitLocalStreamEvent(v: ApplMessage ){
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
        //println("        MQTT ActorBasic $name |  messageArrived on "+ topic + ": "+msg.toString());
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
For direct usage without qak
 */
    fun connectToMqttBroker( mqttAddr : String){
        mqtt.connect(name, mqttAddr )
    }

    fun publish( msg: String, topic: String ){
        mqtt.publish( topic, msg, 1, false);
    }

    fun subscribe(  topic: String ) {
        mqtt.subscribe(this,topic)
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

    fun startTimer() {
        timeAtStart = System.currentTimeMillis()
    }

    fun getDuration() : Int{
        val duration = (System.currentTimeMillis() - timeAtStart).toInt()
        //println("DURATION = $duration")
         solve("retract( wduration(_) )")		//remove old data
         solve("assert( wduration($duration) )")
        return duration
    }

/*
KNOWLEDGE BASE
*/


    fun registerActor( ) {
        //	println("QActorUtils Regsitering in TuProlog ... " + this.getName()  );
        val lib = pengine.getLibrary("alice.tuprolog.lib.OOLibrary")
        //	println("QActorUtils Registering in TuProlog18 ... " + lib );
        val internalName = Struct("" + this.name)
        (lib as alice.tuprolog.lib.OOLibrary).register(internalName, this)
        //	System.out.println("QActorUtils Registered in TuProlog18 " + internalName );
    }

    fun solve( goal: String, rVar: String ="" ) {
        //println("       ActorBasic $name | solveGoal ${goal} rVar=$rVar" );
        val sol = pengine.solve( "$goal.")
        currentSolution = sol
        if(  sol.isSuccess  ) {
            if( (rVar != "") ) {
                val resStr = sol.getVarValue(rVar).toString()
                resVar = sysUtil.strCleaned(resStr)

            }else resVar = "success"
        } else resVar = "fail"
    }
    fun solveOk() : Boolean{
        return resVar != "fail"
    }
    fun getCurSol(v : String) : Term {
        if(currentSolution.isSuccess )
            return currentSolution.getVarValue( v )
        else return Term.createTerm("no(more)solution")
    }
}