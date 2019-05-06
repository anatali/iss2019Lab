package resources.bls.better

import it.unibo.bls.interfaces.IObserver
import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import resources.bls.ButtonAsGui


class buttonGlobalEventEmitter : IObserver {
	
	companion object{
		fun create(){
			val concreteButton = ButtonAsGui.createButton("click")
            concreteButton.addObserver( buttonGlobalEventEmitter() )		
		}
	}
      override fun update(o: Observable?, arg: Any?) {
	   val a = sysUtil.getActor("buttonmqtt")
       if( a is ActorBasic ){
	        GlobalScope.launch{
	            println("buttonGlobalEventEmitter $arg  ${a.name}" )
				a.emit("buttonCmd","buttonCmd(clicked)")				
	        }
	   }
    }
}

 