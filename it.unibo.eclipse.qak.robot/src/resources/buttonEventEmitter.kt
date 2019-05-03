package resources 

import it.unibo.bls.interfaces.IObserver
import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import resources.java.ButtonAsGui

class buttonEventEmitter : IObserver {
	
	companion object{
		val buttonLabels = arrayOf("Stop","Forward","Backward","Left","Rigth","Stop")
		val buttonCmds   = arrayOf("stop","moveforward","movebackward","moveleft","moveright","stop")
		val buttonActor  = sysUtil.getActor("button")
		fun create(){
			val concreteButton = ButtonAsGui.createButton( buttonLabels )
            concreteButton.addObserver( buttonEventEmitter() )			 
		}
	}
      override fun update(o: Observable, arg: Any) {	   
	   //println("buttonEventEmitter update $arg $buttonActor")
       if( buttonActor is ActorBasic ){
		   var cmd ="stop"
		   when( arg as String){
			   buttonLabels[0] -> cmd = buttonCmds[0]
			   buttonLabels[1] -> cmd = buttonCmds[1]
			   buttonLabels[2] -> cmd = buttonCmds[2] 
			   buttonLabels[3] -> cmd = buttonCmds[3]
			   buttonLabels[4] -> cmd = buttonCmds[4]			   
		   }
	       GlobalScope.launch{
				buttonActor.emit("local_buttonCmd","local_buttonCmd($cmd)")				
	       }
	   }
    }
}

 