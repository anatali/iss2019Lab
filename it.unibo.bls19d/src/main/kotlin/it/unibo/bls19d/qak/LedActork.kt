package it.unibo.bls19d.qak

import it.unibo.bls.interfaces.ILed
import it.unibo.bls19d.chain.control.getLedLocation
import it.unibo.chain.segment7.LedSegmHorizontal
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JFrame

class LedActork( name : String ) : ActorBasic( name ){
    val segm: ILed

    init{
        JFrame.setDefaultLookAndFeelDecorated(true)
        val frame = JFrame("Chain")
        frame.setSize( 120, 150)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.layout = GridLayout(1, 1)
        frame.setLocation(getLedLocation() ,100)
        frame.contentPane.background = Color.BLUE
        segm = LedSegmHorizontal(name, 110, 180)
        //segm = LedSegmVerticalRight(name, 110, 180)
        frame.add(segm)
        frame.isVisible = true
    }

    override suspend fun actorBody(msg : ApplMessage){
        println("   LedActork $name |  msg= $msg "  )
        when( msg.msgContent() ){
            "blsMsg(on)"  -> segm.turnOn()
            "blsMsg(off)" -> segm.turnOff()
            else -> println("   LedActork $name | UNKNOWN $msg")
        }
    }
}