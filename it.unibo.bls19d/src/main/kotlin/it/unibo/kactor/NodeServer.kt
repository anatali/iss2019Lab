package it.unibo.kactor

import it.unibo.`is`.interfaces.protocols.IConnInteraction
import it.unibo.supports.FactoryProtocol
import kotlinx.coroutines.*

/*
Works at node level
Waits for a P2P connection with another node
For each connection:
    dispatches a received ApplMessage  to the receiver Actor
 */

class NodeServer(val ctx: NodeContext, val name:String, val protocol: Protocol ) {
    protected var hostName: String? = null
    protected var factoryProtocol: FactoryProtocol? = null

    init {
        System.setProperty("inputTimeOut", "600000")  //10 minuti
        factoryProtocol = MsgUtil.getFactoryProtocol(protocol)
        waitForConnection()
    }

    protected fun waitForConnection() {
        //We could handle several connections
        GlobalScope.launch(Dispatchers.IO) {
            try {
                while (true) {
                    println("   LedServer $name | WAIT FOR CONNECTION")
                    val conn = factoryProtocol!!.createServerProtocolSupport(ctx.portNum) //BLOCKS
                    handleConnection(conn)
                }
            } catch (e: Exception) {
                 println("   LedServer $name | WARNING: ${e.message}")
            }
        }
    }

    protected fun handleConnection(conn: IConnInteraction) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                println("   LedServer | handling new connection:$conn")
                while (true) {
                    val msg = conn.receiveALine()       //BLOCKING
                    //println("   LedServer | receives:$msg")
                    val inputmsg = ApplMessage(msg)
                    val dest     = inputmsg.msgReceiver()
                    if( ctx.hasActor( dest )) MsgUtil.forward(inputmsg, dest )
                    else  println("   LedServer $name | no local actor ${dest}")
                }
            } catch (e: Exception) {
                println("   LedServer $name | handleConnection WARNING: ${e.message}")
            }
        }
    }
}

fun main() : Unit = runBlocking{


}