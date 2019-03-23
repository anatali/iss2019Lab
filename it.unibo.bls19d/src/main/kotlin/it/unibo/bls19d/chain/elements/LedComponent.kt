package it.unibo.bls19d.chain.elements

import it.unibo.bls19d.chain.led.LedActor
import it.unibo.chain.segment7.LedSegmHorizontal
import it.unibo.chain.segment7.LedSegmVerticalRight
import it.unibo.kactor.ActorBasic
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JFrame

class LedComponent( name: String, val location: Int )  {

    lateinit var  ledActor  : ActorBasic

    init{
        val  frame = createFrame()
        val segm = LedSegmHorizontal(name, 110, 180)
        //val segm = LedSegmVerticalRight(name, 110, 180)
        frame.add(segm)
        frame.isVisible = true
        ledActor = LedActor("led$name", segm )
     }

    protected fun  createFrame() : JFrame {
        JFrame.setDefaultLookAndFeelDecorated(true)
        val frame = JFrame("Chain")
        frame.setSize( 120, 150)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.layout = GridLayout(1, 1)
        frame.setLocation(location ,100)
        frame.contentPane.background = Color.BLUE
        return frame
    }

    fun getActor() : ActorBasic {
        return ledActor
    }

}