package it.unibo.kactor.test.chain


import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.delay

class ControlActor( name : String ) : ActorBasic(name){
    var oddClicks     = true
    var goon          = true
    val actors        = sysUtil.ctxActorMap;
    val startBlinkcmd = BlsCmds.ControlCmd("startBlink")
    val stopBlinkcmd  = BlsCmds.ControlCmd("stopBlink")
    val ledOn         = BlsCmds.LedCmd("on")
    val ledOff        = BlsCmds.LedCmd("off")
    val ledActorNamesList : List<String>

    init{
        val ledActors = sysUtil.solve("getActorNames(ACTORS,ANY)", "ACTORS")
        ledActorNamesList = sysUtil.strRepToList(ledActors!!)
    }

    override suspend fun actorBody(msg: ApplMessage){
        //println("ControlActor | receives $msg goon=$goon ")
        when( msg.msgId() ){
            BlsCmds.ButtonCmd.id -> {
                if( oddClicks ){
                    goon = true
                    autoMsg(startBlinkcmd.id, startBlinkcmd.toString())
                }else{
                    goon = false
                    autoMsg(stopBlinkcmd.id, stopBlinkcmd.toString())
                }
                oddClicks = ! oddClicks
            }
            BlsCmds.ControlCmd.id -> {
                if (!goon) return
                when (msg.msgContent()) {
                    "controlCmd(startBlink)" -> {
                        ledActorNamesList.forEach {
                            //println("ControlActor | actor= ${it.key.contains("led")}")
                            if (it.contains("led")) {  //we should FILTER the map
                                forward(ledOn.id, ledOn.toString(),"${it}")
                                delay(200)
                                forward(ledOff.id, ledOff.toString(),"${it}")
                            }
                        }
                        autoMsg(startBlinkcmd.id, startBlinkcmd.toString())
                    }
                    "controlCmd(stopBlink)" -> {
                        goon = false
                        actors.forEach {
                            if( it.key.contains("led") ) {
                                forward(ledOff.id, ledOff.toString(), "${it}")
                            }
                        }
                    }
                    else -> println("ControlActor | $msg unknown")
                }
            }//when msg.msgContent
            else -> println("ControlActor | $msg unknown")
        }//when
    }
}