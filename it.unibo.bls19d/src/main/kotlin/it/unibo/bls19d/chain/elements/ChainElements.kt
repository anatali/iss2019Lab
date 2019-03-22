package it.unibo.bls19d.chain.elements

import it.unibo.bls19d.chain.led.LedActor
import it.unibo.bls19d.chain.led.LedProxy
import it.unibo.bls19d.chain.led.LedServer
import it.unibo.chain.appl.LedInChainCtrlActor
import it.unibo.chain.segment7.LedSegmVerticalRight
import it.unibo.kactor.Protocol
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JFrame
import it.unibo.kactor.*
import it.unibo.bls19d.chain.LedMsg.*

class ChainElements(  ){

    companion object{
          val numOfElements  = 3
    }

    val protocol  = Protocol.TCP
    val portNum   = 8000
    val proxyList : ArrayList<LedProxy> = arrayListOf<LedProxy>()

    init{
        createComponents(   )
        test()
      }

    protected fun  createFrame(i:Int) : JFrame {
        JFrame.setDefaultLookAndFeelDecorated(true)
        val frame = JFrame("Chain")
        frame.setSize( 120, 150)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.layout = GridLayout(1, 1)
        frame.setLocation(100+i*120 ,100)
        frame.contentPane.background = Color.BLUE
        return frame
    }

    protected fun createComponents(  ){
        for( i in 1..numOfElements ){
            val  frame = createFrame(i)
            //val segm = LedSegmHorizontal("segm$i", 110, 180)
            val segm = LedSegmVerticalRight("segm$i", 110, 180)
            //segmList.add(segm)
            frame.add(segm)
            frame.isVisible = true
            //actorList.add(LedInChainCtrlActor("led1", segm))
            val ledPort   = portNum+i*10
            val ledActor  = LedActor("led${i}", segm)
            val server    = LedServer("led${i}server", protocol, ledPort, ledActor)
            val led1Proxy = LedProxy("led${i}proxy", protocol, "localhost", ledPort)
            proxyList.add( led1Proxy )
        }
    }
//For debugging
    fun test(){
        proxyList.forEach{
            //msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM )
            val msgOn = ApplMessage( startBlink.name, "dispatch", "test", it.name, startBlink.cmd, "0")
            MsgUtil.forward(msgOn, it)
        }
        Thread.sleep(3000)
        proxyList.forEach{
            //msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM )
            val msgOff = ApplMessage( stopBlink.name, "dispatch", "test", it.name, stopBlink.cmd, "1")
            MsgUtil.forward(msgOff, it)
        }

        /*
        Register to the controller ??? NO
         */
    }
}
fun main() {
    val elements = ChainElements( )
    //elements.test()
}