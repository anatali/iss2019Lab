package it.unibo.qak.prodConsMqtt

import it.unibo.`is`.interfaces.IObserver
import it.unibo.bls.devices.gui.ButtonAsGui
import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ButtonGuiObserver( val buttonActor : ActorBasic) : IObserver {
      override fun update(o: Observable?, arg: Any?) {
        //println("   ButtonGuiObserver  | UPDATE $arg in $buttonActor" )
        GlobalScope.launch{
            MsgUtil.sendMsg("click", "click", buttonActor)
        }
    }
}

/*
ButtonGuiActork KNOWS that must talk with the actor named "control"
 */
class ButtonGuiActork( name : String, scope: CoroutineScope ) : ActorBasic( name, scope ){
    var working = true

    init{
        val concreteButton = ButtonAsGui.createButton("click")
        concreteButton.addObserver(ButtonGuiObserver(this))
    }
    override suspend fun actorBody(msg : ApplMessage){
        //println("   ButtonGuiActork $name |  msg= $msg working=$working "  )
        when( msg.msgId() ){
            "click" -> {
                //forward( "start", "start", "producer")
                emit("local_start", "local_start")
              }
             //else -> println("   ButtonGuiActork $name | $msg UNKNOWN working=$working")
        }//when
    }
}