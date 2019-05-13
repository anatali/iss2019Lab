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
		val buttonLabels = arrayOf("Alarm","Forward",    "Backward",    "Left",    "Rigth",    "Stop")
		val buttonCmds   = arrayOf("fire",  "moveforward","movebackward","moveleft","moveright","stop")
		lateinit var buttonActor : ActorBasic
		lateinit var msgId       : String
		
		fun create( actor: ActorBasic, msgIdent: String ){
			buttonActor = actor
			msgId       = msgIdent
			val concreteButton = ButtonAsGui.createButton( buttonLabels )
            concreteButton.addObserver( guiSupport() )
		}
	  }
      override fun update(o: Observable, arg: Any) {	   
	   //println("guiSupport update $arg $buttonActor")
  		   var cmd ="stop"
		   when( arg as String ){
			   buttonLabels[0] -> {  //the buttonActor emits an event
				   GlobalScope.launch{
					   buttonActor.emit("envCond","envCond(alarm(${buttonCmds[0]}))")
				   }
				   return
			   } 
			   buttonLabels[1] -> cmd = buttonCmds[1]
			   buttonLabels[2] -> cmd = buttonCmds[2] 
			   buttonLabels[3] -> cmd = buttonCmds[3]
			   buttonLabels[4] -> cmd = buttonCmds[4]			   
		   }
	       GlobalScope.launch{
 			    MsgUtil.sendMsg (msgId , "$msgId($cmd)" , buttonActor)
	       }
     }
}

 