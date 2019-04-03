package it.unibo.bls19d.chain
import it.unibo.bls19d.qak.BlsCmds
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.QakContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
     QakContext.createContexts("localhost",this,
        "src/main/kotlin/it/unibo/bls19d/chain/sysDescr.pl",
        "src/main/kotlin/it/unibo/bls19d/chain/sysRules.pl"
    )

    //IF WE DO NOT ACTIVATE THE BUTTON
    val control = QakContext.getActor("control")
    val outMsg  = BlsCmds.ButtonCmd("clicked")
    MsgUtil.sendMsg(outMsg.id, outMsg.cmd, control!!)
    delay( 2000 )
    MsgUtil.sendMsg(outMsg.id, outMsg.cmd, control!!)
    println("END MAIN SYS0")
}