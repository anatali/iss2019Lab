package it.unibo.kactor

import it.unibo.`is`.interfaces.protocols.IConnInteraction
import it.unibo.supports.FactoryProtocol
import kotlinx.coroutines.*

/*
Works at node level
*/

class QakContextServer(val ctx: QakContext, val name:String, val protocol: Protocol ) { //: ActorBasic(name)
    protected var hostName: String? = null
    protected var factoryProtocol: FactoryProtocol?

    init {
        System.setProperty("inputTimeOut", "600000")  //10 minuti
        factoryProtocol = MsgUtil.getFactoryProtocol(protocol)
        waitForConnection()
    }
/*
    override suspend fun actorBody(msg : ApplMessage){
        println("       QakContextServer $name receives $msg " )
        waitForConnection()
    }
*/
    protected fun waitForConnection() {
        //We could handle several connections
        GlobalScope.launch(Dispatchers.IO) {
            try {
                while (true) {
                    println("       QakContextServer $name | WAIT FOR CONNECTION")
                    val conn = factoryProtocol!!.createServerProtocolSupport(ctx.portNum) //BLOCKS
                    handleConnection(conn)
                }
            } catch (e: Exception) {
                 println("      QakContextServer $name | WARNING: ${e.message}")
            }
        }
    }

    protected fun handleConnection(conn: IConnInteraction) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                println("       QakContextServer $name | handling new connection:$conn")
                while (true) {
                    val msg = conn.receiveALine()       //BLOCKING
                    println("       QakContextServer  $name | receives:$msg")
                    val inputmsg = ApplMessage(msg)
                    val dest     = inputmsg.msgReceiver()
                    val actor    = ctx.hasActor( dest )
                    if( actor is ActorBasic ) MsgUtil.forward(inputmsg, actor )
                    else  println("       QakContextServer $name | no local actor ${dest} in ${ctx.name}")
                }
            } catch (e: Exception) {
                println("       QakContextServer $name | handleConnection WARNING: ${e.message}")
            }
        }
    }
}

fun main() : Unit = runBlocking{


}