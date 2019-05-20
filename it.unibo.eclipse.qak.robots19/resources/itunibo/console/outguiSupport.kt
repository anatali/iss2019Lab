package itunibo.console

import it.unibo.bls.interfaces.IObserver
import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import java.awt.Panel
import java.awt.Color
import java.awt.BorderLayout
 
object outguiSupport {

	lateinit var p :  OutDevPanel
	 		
		fun create( actor: ActorBasic, todo: String  ){
			val frame = Utils.initFrame()
			p = OutDevPanel( 19,60, Color.yellow, Color.black)
			frame.add(BorderLayout.CENTER, p )
			frame.setTitle("OUTPUT");
			frame.validate()
 			//frame.add(BorderLayout.CENTER,p);
		}
		
		fun output( msg : String ){
			p.println( msg )
		}
 
	     
}

 