package it.unibo.bls19d.chain.control

import it.unibo.bls19d.chain.ChainMsg
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.sysUtil

fun main() {
     sysUtil.loadInfo("src\\main\\kotlin\\it\\unibo\\bls19d\\chain\\control\\sysRules.pl",
        "src/main/kotlin/it/unibo/bls19d/chain/control/sysDescr.pl")
    sysUtil.getAndCreatContexts()

    //START: SEND A CMD TO ControlActor
    //msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM )
    val control = sysUtil.ctxActorMap.get("control")
    println("STARTING : ${control!!.name}")

    MsgUtil.sendMsg("main", ChainMsg.startChainBlink.name, ChainMsg.startChainBlink.name, control!!)
    Thread.sleep(3000)
    println(" ---  STOPPING: ${control!!.name}")
    MsgUtil.sendMsg("main", ChainMsg.stopChainBlink.name, ChainMsg.stopChainBlink.name, control!!)


}