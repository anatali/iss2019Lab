package it.unibo.qak.stream

import it.unibo.`is`.interfaces.IObserver
import it.unibo.bls.devices.gui.ButtonAsGui
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class GuiObserver( val buttonActor : ActorBasic) : IObserver {
    override fun update(o: java.util.Observable?, arg: Any?) {
        GlobalScope.launch{
            MsgUtil.sendMsg("click", "click", buttonActor)
        }
    }
}

/*
ButtonGuiActork KNOWS that must talk with the actor named "control"
 */
class ButtonGuiActork( name : String, scope: CoroutineScope) : ActorBasic( name, scope ){
    var working = true

    init{
        val concreteButton = ButtonAsGui.createButton("click")
        concreteButton.addObserver(GuiObserver(this))
    }
    override suspend fun actorBody(msg : ApplMessage){
        //println("   ButtonGuiActork $name |  msg= $msg working=$working "  )
        when( msg.msgId() ){
            "click" -> {

                forward( "start", "start", "producer")
              }
             //else -> println("   ButtonGuiActork $name | $msg UNKNOWN working=$working")
        }//when
    }
}