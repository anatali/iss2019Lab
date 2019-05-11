package resources 

import it.unibo.bls.interfaces.IObserver
import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import resources.java.ButtonAsGui

class guiSupport : IObserver {
	
	companion object{
		val buttonLabels = arrayOf("Stop","Forward","Backward","Left","Rigth","Stop")
		val buttonCmds   = arrayOf("stop","moveforward","movebackward","moveleft","moveright","stop")
		lateinit var buttonActor : ActorBasic
		fun create( actor: ActorBasic, todo: String ){
			buttonActor = actor
			val concreteButton = ButtonAsGui.createButton( buttonLabels )
            concreteButton.addObserver( guiSupport() )
			//println("guiSupport CREATED")					 
		}
	}
      override fun update(o: Observable, arg: Any) {	   
	   //println("guiSupport update $arg $buttonActor")
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
 			    MsgUtil.sendMsg ("robotCmd" , "robotCmd($cmd)" , buttonActor)
	       }
	   }
    }
}

 