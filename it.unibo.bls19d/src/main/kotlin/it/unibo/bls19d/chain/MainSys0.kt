package it.unibo.bls19d.chain

import it.unibo.kactor.sysUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
     sysUtil.loadInfo(
         "src\\main\\kotlin\\it\\unibo\\bls19d\\chain\\sysRules.pl",
        "src/main/kotlin/it/unibo/bls19d/chain/sysDescr.pl")

    //BUILD THE SYSTEM
    sysUtil.createContexts("localhost")

/*
    val control = sysUtil.ctxActorMap.get("control")
    println("STARTING : ${control!!.name}")

    //START: SEND A CMD TO ControlActor
    //msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM )
    val startBlinkcmd = BlsCmds.ControlCmd("startBlink")
    val stopBlinkcmd  = BlsCmds.ControlCmd("stopBlink")
    MsgUtil.sendMsg("main", startBlinkcmd.id, startBlinkcmd.toString(), control!!)
    delay(3000)
    println("STOPPING: ${control!!.name}")
    MsgUtil.sendMsg("main", stopBlinkcmd.id, stopBlinkcmd.toString(), control!!)
    delay(500)
    */
    delay(5000)
    println("ENDING"  )
}