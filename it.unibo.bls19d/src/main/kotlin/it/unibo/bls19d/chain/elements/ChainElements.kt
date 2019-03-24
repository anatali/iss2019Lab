package it.unibo.bls19d.chain.elements

import it.unibo.bls19d.chain.led.LedActorBlink
import it.unibo.bls19d.chain.led.LedProxy
import it.unibo.bls19d.chain.led.LedServer
import it.unibo.chain.segment7.LedSegmVerticalRight
import it.unibo.kactor.Protocol
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JFrame
import it.unibo.kactor.*
import it.unibo.bls19d.chain.ApplLedCmd
import it.unibo.bls19d.chain.LedCmd
import it.unibo.bls19d.chain.LedMsg


/*
Builds numOfElements
 */
class ChainElements(  ){

    companion object{
          val numOfElements  = 3
    }

    val protocol  = Protocol.TCP
    val portNum   = 8000
    val ledActorList : ArrayList<LedActorBlink> = arrayListOf<LedActorBlink>()
    val proxyList    : ArrayList<LedProxy> = arrayListOf<LedProxy>()

    init{
        createComponents(   )
        //localControlTest()
        //Thread.sleep(2000)
        //proxyControlTest()
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
            val ledActor  = LedActorBlink("led${i}", segm)
            ledActorList.add( ledActor )
            LedServer("led${i}server", protocol, ledPort, ledActor)  //START A SERVER
            val led1Proxy = LedProxy("led${i}proxy", protocol, "localhost", ledPort)
            proxyList.add( led1Proxy )
            Thread.sleep(500)
        }
    }

    fun localControlTest(){
    //CONTROL AT LOCAL LEVEL
        val msgOn  = ApplLedCmd( LedMsg.on,"test", "local" )
        val msgOff = ApplLedCmd( LedMsg.off,"test", "local" )
        for( i in 1..3 ) {
            ledActorList.forEach {
                Thread.sleep(200)
                MsgUtil.forward(msgOn, it)
                Thread.sleep(200)
                MsgUtil.forward(msgOff, it)
            }
        }
    }
    fun proxyControlTest(){
        //CONTROL AT PROXY LEVEL
        val msgOn  = ApplLedCmd( LedMsg.on,"test", "server" )
        val msgOff = ApplLedCmd( LedMsg.off,"test", "server" )
        for( i in 1..3 ) {
            proxyList.forEach {
                Thread.sleep(200)
                MsgUtil.forward(msgOn, it)
                Thread.sleep(200)
                MsgUtil.forward(msgOff, it)
            }
        }
    }

}
fun main() {
    val elements = ChainElements( )
    //elements.test()
}