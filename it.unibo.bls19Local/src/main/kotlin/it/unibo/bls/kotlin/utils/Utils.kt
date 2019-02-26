package it.unibo.bls.kotlin.utils

import java.awt.*
import java.awt.event.WindowEvent
import java.awt.event.WindowListener

object Utils {

    @JvmOverloads
    fun initFrame(dx: Int = 400, dy: Int = 200): Frame {
        val frame = Frame()
        val layout = BorderLayout()
        frame.size = Dimension(dx, dy)
        frame.layout = layout
        frame.addWindowListener(object : WindowListener {
            override fun windowOpened(e: WindowEvent) {}
            override fun windowIconified(e: WindowEvent) {}
            override fun windowDeiconified(e: WindowEvent) {}
            override fun windowDeactivated(e: WindowEvent) {}
            override fun windowClosing(e: WindowEvent) {
                System.exit(0)
            }

            override fun windowClosed(e: WindowEvent) {}
            override fun windowActivated(e: WindowEvent) {}
        })
        frame.isVisible = true
        return frame

    }

    fun delay(n: Int) {
        try {
            Thread.sleep(n.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }

}
