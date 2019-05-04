package it.unibo.bls19d.qak

import it.unibo.kactor.*
import kotlinx.coroutines.delay

enum class states{
    INIT, WORK
}

//class ControlActork(name : String, val destName: String ) : ActorBasic( name ){
class ControlActork( name : String  ) : ActorBasic( name ){

    var state    = states.INIT
    var dest     : ActorBasic?
    val onMsg    = BlsCmds.LedCmd("on" )
    val offMsg   = BlsCmds.LedCmd("off")
    val blinkMsg = BlsCmds.ControlCmd("dotBlink")

    init{
        SystemKb.blsActorMap.put( name, this )
        dest = SystemKb.blsActorMap.get("led")
    }
    override suspend fun actorBody(msg : ApplMessage){
        //println("   ControlActork $name |  msg= $msg state=${state} dest=$dest"  )
        if (dest == null  ) dest = SystemKb.blsActorMap.get("led")
        if (dest != null  )
          when (state) {
            states.INIT -> {    //accepts ButtonCmd
                when ( msg.msgId() ) {
                    BlsCmds.ButtonCmd.id ->   {
                        state = states.WORK
                        autoMsg(MsgUtil.buildDispatch(name, blinkMsg.id, blinkMsg.toString(), this.name))
                    }
                    else -> println("   ControlActork $name | $msg UNKNOWN in state:$state")
                 }
            }
            states.WORK -> {    //accepts ButtonCmd ControlCmd
                when ( msg.msgId() ) {
                    BlsCmds.ButtonCmd.id ->   {
                        state = states.INIT
                        forward(offMsg.id, offMsg.toString(), dest!!)
                        return
                    }
                    BlsCmds.ControlCmd.id -> {
                        delay(200)
                        forward(onMsg.id, onMsg.toString(), dest!!)
                        delay(200)
                        forward(offMsg.id, offMsg.toString(), dest!!)
                        autoMsg(MsgUtil.buildDispatch(name, blinkMsg.id, blinkMsg.toString(), this.name))
                        //state = states.WORK
                    }
                }
             }//WORK
            else ->  println("   ControlActork $name | INCONSISTENT")
        }//when(state)
    }
}