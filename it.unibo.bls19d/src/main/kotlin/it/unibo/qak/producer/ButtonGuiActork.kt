package it.unibo.qak.producer

import it.unibo.`is`.interfaces.IObserver
import it.unibo.bls.devices.gui.ButtonAsGui
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ButtonGuiObserver( val buttonActor : ActorBasic) : IObserver {
      override fun update(o: Observable?, arg: Any?) {
        //println("   ButtonGuiObserver  | UPDATE $arg in ${sysUtil.curThread()} $buttonActor" )
        GlobalScope.launch{
            MsgUtil.sendMsg("click", "click", buttonActor)
        }
    }
}

/*
ButtonGuiActork KNOWS that must talk with the actor named "control"
 */
class ButtonGuiActork( name : String ) : ActorBasic( name ){
    var working = true

    init{
        val concreteButton = ButtonAsGui.createButton("click")
        concreteButton.addObserver(ButtonGuiObserver(this))
    }
    override suspend fun actorBody(msg : ApplMessage){
        //println("   ButtonGuiActork $name |  msg= $msg working=$working "  )
        when( msg.msgId() ){
            "click" -> {

                forward( "start", "start", "producer")
              }
             else -> println("   ButtonGuiActork $name | $msg UNKNOWN working=$working")
        }//when
    }
}