package it.unibo.bls19d.qak

import it.unibo.bls.interfaces.IObserver
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ButtonActork( name : String, observer: String ) : IObserver, ActorBasic( name ){
    var working = true
    var dest    : ActorBasic?
    var myself  : ActorBasic

    init{
        val concreteButton = ButtonAsGui.createButton("click")
        concreteButton.addObserver(this)
        dest  = BlsActork.blsActorMap.get(observer) //From name to actor
        myself = this
    }
    override suspend fun actorBody(msg : ApplMessage){
        println("   ButtonActork $name |  msg= $msg working=$working "  )
        when( msg.msgId() ){
            "click" -> {
                val outMsg = BlsCmds.ButtonCmd()
                if (dest is ActorBasic) forward(outMsg.id, outMsg.toString(), dest!!)
            }
                /*
                if( working ){
                    working = false
                    sendStartToControl()
                 }else{
                    working = true
                    sendStopToControl()
                 }
                 */
            else ->  println("   ButtonActork $name | $msg UNKNOWN working=$working")
        }//when
    }
/*
    suspend fun sendStartToControl(){
        val startMsg = BlsCmds.BlinkStartCmd()
        //println("   ButtonActork $name |  outMsg=${startMsg.toString()}   "  )
        if( dest is  ActorBasic) forward( startMsg.id, startMsg.toString(), dest!!  )
    }
    suspend fun sendStopToControl(){
        val stoptMsg = BlsCmds.BlinkStopCmd()
        //println("   ButtonActork $name |   outMsg=${stoptMsg.toString()}"  )
        if( dest is  ActorBasic) forward( stoptMsg.id, stoptMsg.toString(), dest!!  )
    }

*/
     override  fun update(o: Observable?, arg: Any?) {
        println("   ButtonActork $name | UPDATE $arg in ${sysUtil.curThread()}" )
        GlobalScope.launch{
            //val msg = BlsCmds.ButtonCmd()
            //autoMsg(msg.id, msg.toString())  //CANNNOT BE USD HERE
            MsgUtil.sendMsg("click", "click", myself)
        }

    }


}