package itunibo.console

import it.unibo.bls.interfaces.IObserver
import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
 
class guiSupport : IObserver {
	
	companion object{
		val buttonLabels = arrayOf("Alarm"," w(Forward)", "s(Backward)", "a(Left)", "d(Rigth)", "h(Stop)")
		val buttonCmds   = arrayOf("fire",  "w","s","a","d","h")
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
  		   var cmd ="h"
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
			   buttonActor.emit( msgId , "$msgId($cmd)")
 			   //MsgUtil.sendMsg (msgId , "$msgId($cmd)" , buttonActor)
	       }
     }
}

 