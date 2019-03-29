package it.unibo.chain.segment7
import it.unibo.bls.utils.Utils
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JFrame

open class LedSegmVerticalRight(name: String, width: Int, height: Int) : LedSegment(name, width, height) {
    //@Override
    override fun setLedRep() {
        ledRep.addPoint(x + 94,  y + 23)
        ledRep.addPoint(x + 103, y + 18)
        ledRep.addPoint(x + 110, y + 23)
        ledRep.addPoint(x + 110, y + 81)
        ledRep.addPoint(x + 103, y + 90)
        ledRep.addPoint(x + 94,  y + 81)
    }

    companion object {
    /*
    For rapid check
     */
        @JvmStatic
        fun main(arguments: Array<String>) {
            JFrame.setDefaultLookAndFeelDecorated(true)
            val f = JFrame("Segm7")
            f.setSize(240, 200)
            f.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            f.layout = GridLayout(1, 2)

            f.contentPane.background = Color.BLUE
            val secs0 = LedSegmVerticalRight("secs0", 110, 180)
            val secs1 = LedSegmVerticalRight("secs1", 110, 180)
            f.add(secs0)
            f.add(secs1)
            f.isVisible = true

            secs0.turnOn()
            Utils.delay(1000)
            //secs0.turnOff()
            secs1.turnOn()
        }
    }

}
