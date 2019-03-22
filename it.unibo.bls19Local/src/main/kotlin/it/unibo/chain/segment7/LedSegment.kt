package it.unibo.chain.segment7

import it.unibo.bls.interfaces.ILed
import it.unibo.bls.utils.Utils
import javax.swing.*
import java.awt.*

open class LedSegment (protected var myname: String, width: Int, height: Int) : JPanel(),ILed {
    protected var ledRep   = Polygon()
    protected var ledState = false

    init {
        this.name  = myname
        val size = Dimension( width,  height)
        preferredSize = size
        isOpaque = true
        background = Color.black
        println("LedSegment $name | CREATED $width:$height")
        setLedRep()
    }

    open protected fun setLedRep() {
        println("LedSegment $name | setLedRep  x=$x width=$width  ")
        ledRep.addPoint(x + 91, y + 23)
        ledRep.addPoint(x + 98, y + 18)
        ledRep.addPoint(x + 105, y + 23)
        ledRep.addPoint(x + 105, y + 81)
        ledRep.addPoint(x + 98, y + 89)
        ledRep.addPoint(x + 91, y + 81)
    }

      public override fun paintComponent(g: Graphics) {
        //System.out.println("LedSegment | paint");
        super.paintComponent(g) // FIRST this is needed to set the background color
        setSegmentState(g)
    }
    override
    fun turnOn() {
         ledState = true
        repaint()
    }
    override
    fun turnOff() {
         ledState = false
        repaint()
    }
    override
    fun getState() : Boolean{
        return ledState
    }
    protected fun setSegmentState(g: Graphics) {
         if (ledState) g.color = on else g.color = off
        //println("LedSegment $name | setSegmentState $state  ");
        g.fillPolygon(ledRep)
        g.drawPolygon(ledRep)
    }

    companion object {
        private val OFF = 0
        private val ON  = 1
        private val off = Color.green.darker().darker().darker().darker().darker().darker()
        private val on  = Color.green.brighter()
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
            val secs0 = LedSegment("secs0", 110, 180)
            val secs1 = LedSegment("secs1", 110, 180)
            f.add(secs0)
            f.add(secs1)
            f.validate()
            f.isVisible = true

            secs0.turnOn()
            Utils.delay(1000)
            secs0.turnOff()
            secs1.turnOn()
        }
    }
}
