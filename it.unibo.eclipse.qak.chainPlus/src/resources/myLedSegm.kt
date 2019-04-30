package resources

import it.unibo.chain.segment7.LedSegmHorizontal
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JFrame

class myLedSegm : LedSegmHorizontal("myLedSegm", 110, 180) {
  
	companion object{
		var delta = 100
		lateinit var led :  myLedSegm
		fun turnOn() { led.turnOn() }
		fun turnOff() { led.turnOff() }
		fun create() {
			led = myLedSegm()
			led.turnOff()
		}
		fun getX() : Int { delta = delta + 50 ; return   delta }
		fun getY() : Int { delta = delta + 50 ; return   delta }
	}
 
    init{
        JFrame.setDefaultLookAndFeelDecorated(true)
        val frame = JFrame("Chain")
        frame.setSize( 120, 150)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.layout = GridLayout(1, 1)
        frame.setLocation( myLedSegm.getX(), myLedSegm.getY() )
        frame.contentPane.background = Color.BLUE
        frame.add(this)
        frame.isVisible = true		
    }

}