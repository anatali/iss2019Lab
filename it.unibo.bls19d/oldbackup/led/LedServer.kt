package it.unibo.bls19d.chain.led

import it.unibo.`is`.interfaces.protocols.IConnInteraction
import it.unibo.bls19d.chain.LedMsg
import it.unibo.chain.segment7.LedSegmHorizontal
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.Protocol
import it.unibo.supports.FactoryProtocol
import kotlinx.coroutines.*
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JFrame

/*
Not an ActorBasic, since it must use Dispatchers.IO
*/
class LedServer(  name:String, val protocol: Protocol, val portNum: Int,
                val ledActor: ActorBasic )  :  ActorBasic( name, true ) {
    protected var hostName: String? = null
    protected var factoryProtocol: FactoryProtocol? = null

    init {
        System.setProperty("inputTimeOut", "600000")  //10 minuti
        factoryProtocol = MsgUtil.getFactoryProtocol(protocol)
        //waitForConnection()
        GlobalScope.launch(Dispatchers.IO) {
            autoMsg( "start", "start" )
        }
        //MsgUtil.sendMsg("start", "start", this.actor )
    }

    override suspend fun actorBody(msg : ApplMessage){
        println("       LedServer $name receives $msg  ")
        waitForConnection()
    }

    suspend protected fun waitForConnection() {
        //We could handle several connections
        //GlobalScope.launch(Dispatchers.IO) {
            try {
                while (true) {
                    println("   LedServer $name | WAIT FOR CONNECTION")
                    val conn = factoryProtocol!!.createServerProtocolSupport(portNum) //BLOCKS
                    handleConnection(conn)
                }
            } catch (e: Exception) {
                //e.printStackTrace()
                println("   LedServer $name | WARNING: ${e.message}")
            }
        //}
    }

    suspend  protected fun handleConnection(conn: IConnInteraction) {
        //GlobalScope.launch(Dispatchers.IO) {
            try {
                println("   LedServer | handling new connection:$conn")
                while (true) {
                    val msg = conn.receiveALine()       //BLOCKING
                    println("   LedServer | receives:$msg")
                    val inputmsg = ApplMessage(msg)
                    MsgUtil.sendMsg(inputmsg, ledActor)
                }
            } catch (e: Exception) {
                println("   LedServer $name | handleConnection WARNING: ${e.message}")
            }
        //}
    }
}

fun main() : Unit = runBlocking{
    val protocol  = Protocol.TCP
    val portNum   = 8010
    /*
    //CREATE A LedSegment
    JFrame.setDefaultLookAndFeelDecorated(true)
    val frame = JFrame("Led")
    frame.setSize(120, 100)
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.layout = GridLayout(1, 1)
    frame.contentPane.background = Color.BLUE
    val ledgui    = LedSegmHorizontal("s0", 110, 180)
    ledgui.background = Color.BLACK
    //frame.add( ledgui )
    frame.isVisible = true
*/
    //CREATE THE LED ACTOR
    val ledactor      = LedActor("led1" )
    //CREATE THE LED SERVER
    val ledServer = LedServer("led1server", protocol, portNum, ledactor)
    delay(500)


    for( i in 1..3 ) {
        //CREATE THE LED PROXY  connected with the LED SERVER
        GlobalScope.launch {
            println("STARTING LED $i ... ")
            val ledProxy = LedProxy("led${i}proxy", protocol, "localHost", portNum)
            delay(1000)
            println("WORKING LED $i ... ")
            //SEND dispacth commands to the LED PROXY
            ledProxy.forward("on", "on", ledProxy)
            delay(4000)
            ledProxy.forward("off", "off", ledProxy)
        }
    }
}