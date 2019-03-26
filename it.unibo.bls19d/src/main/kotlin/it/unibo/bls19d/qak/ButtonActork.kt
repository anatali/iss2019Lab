package it.unibo.bls19d.qak


import it.unibo.bls.interfaces.IObserver
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ButtonActork( name : String ) : IObserver, ActorBasic( name ){
    var working = true
    var dest    : ActorBasic?
    var myself  : ActorBasic

    init{
        val concreteButton = ButtonAsGui.createButton("click")
        concreteButton.addObserver(this)
        dest  = blsActorMap.get("control") //From name to actor
        myself = this
    }
    override suspend fun actorBody(msg : ApplMessage){
        println("   ButtonActork $name |  msg= $msg working=$working "  )
        when( msg.msgContent() ){
            "blsMsg(click)" ->
                if( working ){
                    working = false
                    sendStartToControl()
                 }else{
                    working = true
                    sendStopToControl()
                 }
            else ->  println("   ButtonActork $name | $msg UNKNOWN working=$working")
        }//when
    }

    suspend fun sendStartToControl(){
        val startMsg = BlsCmd.BlinkStartCmd()
        println("   ButtonActork $name |  outMsg=${startMsg.toString()}   "  )
        if( dest is  ActorBasic) forward( startMsg.id, startMsg.toString(), dest!!  )
    }
    suspend fun sendStopToControl(){
        val stoptMsg = BlsCmd.BlinkStopCmd()
         println("   ButtonActork $name |   outMsg=${stoptMsg.toString()}"  )
        if( dest is  ActorBasic) forward( stoptMsg.id, stoptMsg.toString(), dest!!  )
    }


    override fun update(o: Observable?, arg: Any?) {
        println("   ButtonActork $name | UPDATE $arg in ${sysUtil.curThread()}" )
        GlobalScope.launch{
            val msg = BlsCmd.ButtonCmd()
            //autoMsg(msg.id, msg.toString())
            MsgUtil.sendMsg("${msg.id}", "${msg.toString()}", myself)
        }

    }


}