package it.unibo.bls19d.qak

import it.unibo.blsFramework.kotlin.fsm.states
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.delay

enum class states{
    WORK, HALT
}

class ControlActork(name : String ) : ActorBasic( name ){

    var state  = states.HALT
    val dest   = blsActorMap.get("led") //: ActorBasic?
    val onMsg  = BlsCmd.LedOn()
    val offMsg = BlsCmd.LedOff()
    val startMsg = BlsCmd.BlinkStartCmd()
    var stopped = false

    init{
       // state  = states.HALT
       // dest   = blsActorMap.get("led")
    }
    override suspend fun actorBody(msg : ApplMessage){
        println("   ControlActork $name |  msg= $msg state=${state}"  )
        if (dest is ActorBasic)
          when (state) {
            states.HALT -> {
                when (msg.msgContent()) {
                    "blsMsg(startBlink)" ->   {
                        state = states.WORK
                        autoMsg(MsgUtil.buildDispatch(name, startMsg.id, startMsg.toString(), this.name))
                    }
                    else -> {
                        println("   ControlActork $name | $msg UNKNOWN in state $state")
                    }
                }
            }
            states.WORK -> {
                when (msg.msgContent()) {
                    "blsMsg(startBlink)" ->   {
                        if( stopped ){
                            stopped = false
                            return
                        }
                        delay(500)
                        forward(onMsg.id, onMsg.toString(), dest)
                        delay(500)
                        forward(offMsg.id, offMsg.toString(), dest)
                        autoMsg(MsgUtil.buildDispatch(name, startMsg.id, startMsg.toString(), this.name))
                        //state = states.WORK
                    }
                    "blsMsg(stopBlink)" -> {
                        forward(offMsg.id, offMsg.toString(), dest)
                        stopped = true
                        state = states.HALT
                    }
                    else -> {
                        println("   ControlActork $name | $msg UNKNOWN in state $state")
                    }
                }
            }//WORK
            else -> {}
        }//when(state)

    }
}