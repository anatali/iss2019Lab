package it.unibo.bls19d.chain

import it.unibo.`is`.interfaces.IObserver
import it.unibo.bls19d.qak.BlsCmds
import it.unibo.bls19d.qak.ButtonAsGui
import it.unibo.kactor.*
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
        concreteButton.addObserver( ButtonGuiObserver( this ) )
    }
    override suspend fun actorBody(msg : ApplMessage){
        //println("   ButtonGuiActork $name |  msg= $msg working=$working "  )
        when( msg.msgId() ){
            "click" -> {
                val outMsg = BlsCmds.ButtonCmd("clicked")
                forward( outMsg.id, outMsg.toString(), "control")
              }
             else -> println("   ButtonGuiActork $name | $msg UNKNOWN working=$working")
        }//when
    }
}