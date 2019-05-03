/*
Copied from project it.unibo.bls19d with small changes:
 
 sysUtil INSTEAD OF SystemKb   				   (1)
 constructor changed 						   (2)
 destination actor (control) found at run time (3) 
 */
package resources.bls

import it.unibo.bls.interfaces.IObserver
import it.unibo.kactor.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlinx.coroutines.CoroutineScope	//added  (0)

class ButtonObserver( val buttonActorName : String) : IObserver {
    var buttonActor : ActorBasic? = null
    //BlsActork.blsActorMap.get(butonActorName) at the monent resturs null
    //since the ButtonActork has not  built yet. So we set buttonActor in update
    override fun update(o: Observable?, arg: Any?) {
        //println("   ButtonObserver  | UPDATE $arg in ${sysUtil.curThread()} $buttonActorName" )
        if(  ! ( buttonActor is ActorBasic ) ) {  //we must bind	(1)
            buttonActor = sysUtil.getActor(buttonActorName) //SystemKb.blsActorMap.get(buttonActorName)
            println("   ButtonObserver  | BINDS $buttonActor" )
        }
        GlobalScope.launch{
            MsgUtil.sendMsg("click", "click", buttonActor!!)
        }
    }
}

//class ButtonActork( name : String, val destName: String ) : ActorBasic( name ){
class ButtonActork( name : String, scope: CoroutineScope   ) : ActorBasic( name, scope ){  //(2)
    var working = true
    var dest    : ActorBasic?

    init{
        val concreteButton = ButtonAsGui.createButton("click")
        dest  = sysUtil.getActor("control") //From name to actor  TOO EARLY HERE (3)
        concreteButton.addObserver( ButtonObserver( name ) )
     }
    override suspend fun actorBody(msg : ApplMessage){
        if (dest is ActorBasic)
			//println("   ButtonActork $name |  msg= $msg working=$working dest=${dest!!.name}"  )
        else dest  = sysUtil.getActor("control") //From name to actor
		when( msg.msgId() ){
            "click" -> {
                val outMsg = BlsCmds.ButtonCmd("clicked")
                if (dest is ActorBasic) forward(outMsg.id, outMsg.toString(), dest!!)
            }
             else -> println("   ButtonActork $name | $msg UNKNOWN working=$working")
        }//when
    }
}