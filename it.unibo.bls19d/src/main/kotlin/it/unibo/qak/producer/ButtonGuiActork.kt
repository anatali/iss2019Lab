package it.unibo.qak.producer

import devices.gui.ButtonAsGui
import it.unibo.`is`.interfaces.IObserver
import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope
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
                emit( "local_start", "5" )
              }
             //else -> println("   ButtonGuiActork $name | $msg UNKNOWN working=$working")
        }//when
    }
}