package it.unibo.bls19d.qak

import it.unibo.bls.interfaces.IObserver
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ButtonObserver( val buttonActorName : String) : IObserver {
    var buttonActor : ActorBasic? = null
    //BlsActork.blsActorMap.get(butonActorName) at the monent resturs null
    //since the ButtonActork has not  built yet. So we set buttonActor in update
    override fun update(o: Observable?, arg: Any?) {
        println("   ButtonObserver  | UPDATE $arg in ${sysUtil.curThread()} $buttonActorName" )
        if(  ! ( buttonActor is ActorBasic ) ) {  //we must bind
            buttonActor = SystemKb.blsActorMap.get(buttonActorName)
            println("   ButtonObserver  | BINDS $buttonActor" )
        }
        GlobalScope.launch{
            MsgUtil.sendMsg("click", "click", buttonActor!!)
        }
    }
}

class ButtonActork( name : String, val destName: String ) : ActorBasic( name ){
    var working = true
    var dest    : ActorBasic?
    var myself  : ActorBasic

    init{
        val concreteButton = ButtonAsGui.createButton("click")
        concreteButton.addObserver( ButtonObserver( destName ) )
        dest  = SystemKb.blsActorMap.get(destName) //From name to actor
        myself = this
    }
    override suspend fun actorBody(msg : ApplMessage){
        println("   ButtonActork $name |  msg= $msg working=$working "  )
        when( msg.msgId() ){
            "click" -> {
                val outMsg = BlsCmds.ButtonCmd()
                if (dest is ActorBasic) forward(outMsg.id, outMsg.toString(), dest!!)
            }
             else -> println("   ButtonActork $name | $msg UNKNOWN working=$working")
        }//when
    }
}