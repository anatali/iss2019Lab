package it.unibo.bls19d.qak


import devices.gui.segm7.LedSegmentHorizontal
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JFrame

class LedSegm : LedSegmentHorizontal("ledSegm", 110, 180) {

    init{
        JFrame.setDefaultLookAndFeelDecorated(true)
        val frame = JFrame("Chain")
        frame.setSize( 120, 150)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.layout = GridLayout(1, 1)
        frame.setLocation(100,200)
        frame.contentPane.background = Color.BLUE
        //segm = LedSegmHorizontal(name, 110, 180)
        //segm = LedSegmVerticalRight(name, 110, 180)
        frame.add(this)
        frame.isVisible = true
    }

}