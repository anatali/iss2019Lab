package resources.bls.better

import it.unibo.bls.interfaces.IObserver
import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import resources.bls.ButtonAsGui


class buttonEventEmitter : IObserver {
	
	companion object{
		fun create(){
			val concreteButton = ButtonAsGui.createButton("click")
            concreteButton.addObserver( buttonEventEmitter() )		
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

 