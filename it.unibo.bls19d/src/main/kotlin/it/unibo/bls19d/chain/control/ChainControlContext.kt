package it.unibo.bls19d.chain.control

import it.unibo.bls19d.chain.ChainMsg
import it.unibo.kactor.*

fun main() {

    sysUtil.loadInfo("src\\main\\kotlin\\it\\unibo\\bls19d\\chain\\control\\sysRules.pl",
        "src/main/kotlin/it/unibo/bls19d/chain/control/sysDescr.pl")
    sysUtil.getAndCreatContexts()

    //START: SEND A CMD TO ControlActor
    //msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM )
    val main = sysUtil.ctxActorMap.get("control")
    println("STARTING: $main")
    //main?.forward("cmd","on",main)

    main?.forward(ChainMsg.startChainBlink.name, ChainMsg.startChainBlink.name, main )
    Thread.sleep(3000)
    println(" ----------------------------------------------- STOPPING: $main")
    main?.forward(ChainMsg.stopChainBlink.name, ChainMsg.stopChainBlink.name, main )

}