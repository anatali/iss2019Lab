package resources

import it.unibo.bls.interfaces.IObserver
import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import it.unibo.bls.devices.gui.ButtonAsGui

class buttonForChain : IObserver {
	
	companion object{
		fun create(){
			val concreteButton = ButtonAsGui.createButton("click")
            concreteButton.addObserver( buttonForChain() )		
		}
	}
      override fun update(o: Observable?, arg: Any?) {
	   val a = sysUtil.getActor("button")
       if( a is ActorBasic ){
	        GlobalScope.launch{
	            //MsgUtil.sendMsg("click", "clicked", a)
				a.emit("local_buttonCmd","local_buttonCmd(clicked)")				
	        }
	   }
    }
}

 