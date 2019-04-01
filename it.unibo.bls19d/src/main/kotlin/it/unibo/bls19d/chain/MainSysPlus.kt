package it.unibo.bls19d.chain

import it.unibo.bls19d.qak.BlsCmds
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
     sysUtil.loadInfo(
         "src\\main\\kotlin\\it\\unibo\\bls19d\\chain\\sysRules.pl",
        "src/main/kotlin/it/unibo/bls19d/chain/sysDescr.pl")
    //BUILD THE SYSTEM
    //sysUtil.createContexts("localhost")
    sysUtil.createContexts("192.168.43.5")
}