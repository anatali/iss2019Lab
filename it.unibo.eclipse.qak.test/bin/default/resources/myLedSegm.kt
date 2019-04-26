package resources

import it.unibo.chain.segment7.LedSegmHorizontal
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JFrame

class myLedSegm : LedSegmHorizontal("myLedSegm", 110, 180) {
  
	companion object{
		lateinit var led :  myLedSegm
		fun turnOn() { led.turnOn() }
		fun turnOff() { led.turnOff() }
		fun create() {
			led = myLedSegm()
			led.turnOff()
		}
	}
 
    init{
        JFrame.setDefaultLookAndFeelDecorated(true)
        val frame = JFrame("Chain")
        frame.setSize( 120, 150)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.layout = GridLayout(1, 1)
        frame.setLocation(100,200)
        frame.contentPane.background = Color.BLUE
        frame.add(this)
        frame.isVisible = true		
    }

}