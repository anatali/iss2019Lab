package resources.bls.better

import it.unibo.bls.interfaces.IObserver
import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import resources.bls.ButtonAsGui


class buttonEventEmitter( val a : ActorBasic, val name : String ) : IObserver {
	
	companion object{
		
		fun create(   ){
			val concreteButton = ButtonAsGui.createButton( "click" )
			val a = sysUtil.getActor("button")
            concreteButton.addObserver( buttonEventEmitter( a!!, "click") )		
		}
		fun create( a : ActorBasic, name : String ){
			val concreteButton = ButtonAsGui.createButton(name)
            concreteButton.addObserver( buttonEventEmitter(a,name) )		
		}
	}
      override fun update(o: Observable?, arg: Any?) {
	   //val a = sysUtil.getActor("button")
       //if( a is ActorBasic ){
	        GlobalScope.launch{
	            //MsgUtil.sendMsg("click", "clicked", a)
				println("click")
				a.emit("local_buttonCmd","local_buttonCmd($name)")				
	        }
	   //}
    }
}

 