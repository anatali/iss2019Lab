package it.unibo.bls19d.chain.led

import it.unibo.bls.interfaces.ILed
import it.unibo.bls19d.chain.control.getLedLocation
import it.unibo.chain.segment7.LedSegmHorizontal
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JFrame


open class LedActor( name: String) : ActorBasic(name) {
    val segm: ILed

    init{
        val  frame = createFrame()
        segm = LedSegmHorizontal(name, 110, 180)
        //segm = LedSegmVerticalRight(name, 110, 180)
        frame.add(segm)
        frame.isVisible = true

    }
    protected fun  createFrame() : JFrame {
        JFrame.setDefaultLookAndFeelDecorated(true)
        val frame = JFrame("Chain")
        frame.setSize( 120, 150)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.layout = GridLayout(1, 1)
        frame.setLocation(getLedLocation() ,100)
        frame.contentPane.background = Color.BLUE
        return frame
    }

    override suspend fun actorBody( cmd : ApplMessage){
        //println( "LedActor $name | RECEIVED $cmd " )
        when( cmd.msgContent()  ){
            "on" -> {
                segm.turnOn();
            }
            "off" -> {
                segm.turnOff();
            }
            else ->
            println("LedActor does not handle ${cmd.msgContent()} ")
            //throw Exception("LedActor does not handle ${cmd.msgContent()} ")
        }
      }
}

/* Rapid check */
fun main() : Unit = runBlocking{
    val ledActor = LedActor("ledActor" )
    //MsgUtil.forward( LedMsg.startBlink.name, LedMsg.startBlink.cmd, led1 )
    delay(1000)
   //CONTROL
      for( i in 1..3 ) {
        delay(500)
        ledActor.forward("ledCmd", "on", ledActor)
        delay(500)
        ledActor.forward("ledCmd", "off", ledActor)
    }

}
