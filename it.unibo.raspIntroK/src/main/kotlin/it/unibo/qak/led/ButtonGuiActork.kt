package it.unibo.qak.led

import it.unibo.`is`.interfaces.IObserver
import it.unibo.bls.devices.gui.ButtonAsGui
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

    init{
        val concreteButton = ButtonAsGui.createButton("click")
        concreteButton.addObserver(ButtonGuiObserver(this))
    }
    override suspend fun actorBody(msg : ApplMessage){
        println("   ButtonGuiActork $name |  msg= $msg  " )
        when( msg.msgId() ){
            "click" -> {
                emit( BlsCmds.ButtonCmd.id, "clicked" )
              }
             else -> println("   ButtonGuiActork $name | $msg UNKNOWN  ")
        }//when
    }
}