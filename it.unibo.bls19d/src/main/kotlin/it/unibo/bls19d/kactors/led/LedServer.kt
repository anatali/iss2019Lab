package it.unibo.bls19d.kactors.led

import it.unibo.bls19d.kactors.LedMsg
import it.unibo.chain.segment7.LedSegmHorizontal
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.Protocol
import it.unibo.supports.FactoryProtocol
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JFrame

class LedServer(val name:String, val protocol: Protocol, val portNum: Int,
                val ledActor: ActorBasic )   {
    protected var hostName: String? = null
    protected var factoryProtocol: FactoryProtocol? = null

    init {
        factoryProtocol = MsgUtil.getFactoryProtocol(protocol)
        doJob()
    }

    protected fun doJob() {
        GlobalScope.launch {
            try {
                println("   LedFrontEnd | STARTED")
                val conn = factoryProtocol!!.createServerProtocolSupport(portNum) //BLOCKS
                while (true) {
                    val msg = conn.receiveALine()       //BLOCKING
                    println("   LedFrontEnd | receives:$msg")
                    val inputmsg = ApplMessage(msg)
                    MsgUtil.forward(inputmsg, ledActor)
                    //setChanged()
                    //notifyObservers( inputmsg.msgContent() )
                }
            } catch (e: Exception) {
                e.printStackTrace() }
        }
    }
}

fun main() : Unit = runBlocking{
    val protocol  = Protocol.TCP
    val portNum   = 8010
    //CREATE A LedSegment
    JFrame.setDefaultLookAndFeelDecorated(true)
    val frame = JFrame("Led")
    frame.setSize(120, 100)
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.layout = GridLayout(1, 1)
    frame.contentPane.background = Color.BLUE
    val ledgui    = LedSegmHorizontal("s0", 110, 180)
    ledgui.background = Color.BLACK
    frame.add( ledgui )
    frame.isVisible = true

    //CREATE THE LED ACTOR
    val led1      = LedActor("led1", ledgui)
    //CREATE THE LED SERVER
    val ledServer = LedServer("led1server", protocol, portNum, led1)
    delay(500)
    //CREATE THE LED PROXY  connected with the LED SERVER
    val led1Proxy = LedProxy("led1proxy", protocol, "localHost", portNum)
    delay(1000)
    println("WORKING ... ")
    //SEND dispacth commands to the LED PROXY
    led1Proxy.forward( LedMsg.startBlink.name, LedMsg.startBlink.cmd,led1Proxy)
    delay(4000)
    led1Proxy.forward( LedMsg.stopBlink.name, LedMsg.stopBlink.cmd, led1Proxy )

}