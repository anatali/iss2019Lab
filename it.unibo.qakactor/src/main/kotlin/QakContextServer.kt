package it.unibo.kactor

import it.unibo.`is`.interfaces.protocols.IConnInteraction
import it.unibo.supports.FactoryProtocol
import kotlinx.coroutines.*

/*
Works at node level
*/

class QakContextServer(val ctx: QakContext, scope: CoroutineScope,
                       name:String, val protocol: Protocol ) : ActorBasic( name, scope) {
    protected var hostName: String? = null
    protected var factoryProtocol: FactoryProtocol?

    init {
        System.setProperty("inputTimeOut", "600000")  //10 minuti
        factoryProtocol = MsgUtil.getFactoryProtocol(protocol)
        scope.launch(Dispatchers.IO) {
            autoMsg( "start", "startQakContextServer" )
        }
    }

    override suspend fun actorBody(msg : ApplMessage){
        //println("      QakContextServer $name receives $msg  ")
        waitForConnection()
    }

    suspend protected fun waitForConnection() {
        //We could handle several connections
        GlobalScope.launch(Dispatchers.IO) {
            try {
                while (true) {
                    //println("       QakContextServer $name | WAIT FOR CONNECTION")
                    val conn = factoryProtocol!!.createServerProtocolSupport(ctx.portNum) //BLOCKS
                    handleConnection(conn)
                }
            } catch (e: Exception) {
                 println("      QakContextServer $name | WARNING: ${e.message}")
            }
        }
    }
/*
EACH CONNECTION WORKS IN ITS OWN COROUTINE
 */
    suspend protected fun handleConnection(conn: IConnInteraction) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                //println("       QakContextServer $name | handling new connection:$conn")
                while (true) {
                    val msg = conn.receiveALine()       //BLOCKING
                    //println("       QakContextServer  $name | receives:$msg in ${sysUtil.curThread()}")
                    val inputmsg = ApplMessage(msg)
                    if( inputmsg.msgType() == ApplMessageType.event.toString() ){
                        propagateEvent(inputmsg)
                        continue
                    }
                    val dest     = inputmsg.msgReceiver()
                    val actor    = ctx.hasActor( dest )
                    if( actor is ActorBasic ){
                        try{
                            MsgUtil.sendMsg(inputmsg, actor )
                        }catch( e1 : Exception) {
                            println("       QakContextServer $name | WARNING: ${e1.message}")
                         }
                    } else  println("       QakContextServer $name | WARNING!! no local actor ${dest} in ${ctx.name}")
                }
            } catch (e: Exception) {
                println("       QakContextServer $name | handleConnection WARNING: ${e.message}")
            }
        }
    }//handleConnection

    suspend fun propagateEvent(event : ApplMessage){
         ctx.actorMap.forEach{
           // println("       QakContextServer $name | in ${ctx.name} propag $event to ${it.key} in ${it.value.context.name}")
             val a = it.value
             try{
                 a.actor.send(event)
             }catch( e1 : Exception) {
                println("       QakContextServer $name | propagateEvent WARNING: ${e1.message}")
             }
         }
    }
}

