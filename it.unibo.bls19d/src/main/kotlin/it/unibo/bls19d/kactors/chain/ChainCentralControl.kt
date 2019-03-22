package it.unibo.bls19d.kactors.chain

import alice.tuprolog.Struct
import alice.tuprolog.Term
import it.unibo.bls.interfaces.ILed

import it.unibo.bls19d.kactors.ChainMsg
import it.unibo.bls19d.kactors.ChainRegister
import it.unibo.bls19d.kactors.led.LedActor
import it.unibo.bls19d.kactors.led.LedProxy
import it.unibo.bls19d.kactors.led.LedServer
import it.unibo.chain.segment7.LedSegmHorizontal
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.Protocol
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JFrame

import it.unibo.bls19d.kactors.LedMsg.*

class ChainCentralControl( name: String ) : ActorBasic(name) {
val ledList : MutableList<LedProxy>  = mutableListOf<LedProxy>()

    override suspend fun actorBody( msg : ApplMessage ){
        println("RECEIVED: $msg")
        when( msg.msgId() ){
           "chainRegister" -> {
               println("${msg.msgContent()}")
               val t = Term.createTerm( msg.msgContent() )
               val ts = t as Struct
               val host=ts.getArg(0).toString()
               val port=ts.getArg(1).toString()
               val protocol = ts.getArg(2).toString()
               val portNum = Integer.parseInt(port)

               ledList.add( LedProxy("led", Protocol.TCP, host, portNum) )
               println("chainRegister ${host} , $portNum")
           }
            ChainMsg.startChainBlink.name -> {
                println("blink listSize=${ledList.size}")
                ledList.forEach{
                    it.forward( startBlink.name, startBlink.cmd,it)
                }

            }
            ChainMsg.stopChainBlink.name -> {
                println("no blink")
                ledList.forEach{
                    it.forward( stopBlink.name, stopBlink.cmd,it)
                }
            }
            else -> println("Unknown")
        }
    }
}


//registerLed( hostName, port )

fun main() : Unit = runBlocking{
    val hostName  = "localhost"
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
    //val led1Proxy = LedProxy("led1proxy", protocol, hostName, portNum)



    val ccc = ChainCentralControl("ccc")
    val msg = ChainRegister("localHost", portNum.toString() )
    ccc.forward(msg.msgId,msg.toString(), ccc)
    //We must create many ledServer

    //ccc.forward(msg.msgId,msg.toString(), ccc)
    delay(1000)
    ccc.forward(ChainMsg.startChainBlink.name,ChainMsg.startChainBlink.name, ccc)
    delay(3000)
    ccc.forward(ChainMsg.stopChainBlink.name,ChainMsg.stopChainBlink.name, ccc)
}