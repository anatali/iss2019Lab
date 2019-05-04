package it.unibo.bls19d.qak


import it.unibo.`is`.interfaces.IObserver
import it.unibo.kactor.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ButtonObserver( val buttonActorName : String) : IObserver {
    var buttonActor : ActorBasic? = null
    //BlsActork.blsActorMap.get(butonActorName) at the monent resturs null
    //since the ButtonActork has not  built yet. So we set buttonActor in update
    override fun update(o: Observable?, arg: Any?) {
        //println("   ButtonObserver  | UPDATE $arg in ${sysUtil.curThread()} $buttonActorName" )
        if(  ! ( buttonActor is ActorBasic ) ) {  //we must bind
            //buttonActor = SystemKb.blsActorMap.get(buttonActorName)
            buttonActor = SystemKb.blsActorMap.get(buttonActorName)
            //println("   ButtonObserver  | BINDS $buttonActor" )
        }
        GlobalScope.launch{
            MsgUtil.sendMsg("click", "click", buttonActor!!)
        }
    }
}


class ButtonActork  : ActorBasic {
    var working  = true
    var destName = ""
    var dest : ActorBasic? = null

    constructor( name : String  ) : super(name){
        destName = "control"
        configure()
        //dest  = SystemKb.blsActorMap.get(destName)
    }
    constructor( name : String, destName: String ) : super( name ){
        this.destName = destName
        configure()
    }

    fun configure(){
        val concreteButton = ButtonAsGui.createButton("click")
        dest  = SystemKb.blsActorMap.get(destName) //From name to actor
        //If we do ButtonObserver( destName ) then the "click" is sne to destName DIRECTLY
        SystemKb.blsActorMap.put( name, this )
        concreteButton.addObserver( ButtonObserver( name ) )
     }
    override suspend fun actorBody(msg : ApplMessage){
        println("   ButtonActork $name |  msg= $msg working=$working destName=$dest"  )
        when( msg.msgId() ){
            "click" -> {
                val outMsg = BlsCmds.ButtonCmd("clicked")
                if (dest is ActorBasic) forward(outMsg.id, outMsg.toString(), dest!!)
            }
             else -> println("   ButtonActork $name | $msg UNKNOWN working=$working")
        }//when
    }
}