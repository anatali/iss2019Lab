package resources

import it.unibo.chain.segment7.LedSegmHorizontal
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JFrame

class myLedSegm : LedSegmHorizontal("myLedSegm", 110, 180) {
var frame : JFrame
	companion object{
		var deltaX = 50
		var deltaY = 50
		lateinit var led :  myLedSegm
		fun turnOn() { led.turnOn() }
		fun turnOff() { led.turnOff() }
		fun create() {
			led = myLedSegm()
			led.turnOff()
		}
		fun getX() : Int { deltaX = deltaX + 150  ; return   deltaX }
		fun getY() : Int { return   deltaY }
	}
 
    init{
        JFrame.setDefaultLookAndFeelDecorated(true)
        frame = JFrame("Chain")
        frame.setSize( 120, 150)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.layout = GridLayout(1, 1)
        frame.setLocation( myLedSegm.getX(), myLedSegm.getY() )
        frame.contentPane.background = Color.BLUE
        frame.add(this)
        frame.isVisible = true		
    }
	
	fun hideLed(){
		frame.setSize( 40, 50)
		//frame.isVisible = false
	}

}