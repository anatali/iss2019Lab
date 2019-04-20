package qastate

import it.unibo.chain.segment7.LedSegmHorizontal
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JFrame

class LedSegm : LedSegmHorizontal("ledSegm", 110, 180) {

    init{
        JFrame.setDefaultLookAndFeelDecorated(true)
        val frame = JFrame("Chain")
        frame.setSize( 120, 150)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.layout = GridLayout(1, 1)
        frame.setLocation(getLedLocation(),200)
        frame.contentPane.background = Color.BLUE
        //segm = LedSegmHorizontal(name, 110, 180)
        //segm = LedSegmVerticalRight(name, 110, 180)
        frame.add(this)
        frame.isVisible = true
    }

}