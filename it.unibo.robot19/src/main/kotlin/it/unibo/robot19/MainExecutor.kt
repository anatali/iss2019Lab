package it.unibo.robot19

import it.unibo.kactor.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    QakContext.createContexts(
        "localhost",
        this,
        "src/main/kotlin/it/unibo/robot19/executorDescr.pl",
        "src/main/kotlin/it/unibo/robot19/sysRules.pl"
    )

    val robot = QakContext.getActor("robot")
    MsgUtil.sendMsg(RobotCmds.id, RobotCmds.turnLeft.toString(), robot!!)
    delay( 1000 )
    MsgUtil.sendMsg(RobotCmds.id, RobotCmds.turnRight.toString(), robot!!)
    delay( 1000 )
    MsgUtil.sendMsg(RobotCmds.id, RobotCmds.forward.toString(), robot!!)
    delay( 1000 )
    MsgUtil.sendMsg(RobotCmds.id, RobotCmds.backward.toString(), robot!!)
    delay( 1000 )
    MsgUtil.sendMsg(RobotCmds.id, RobotCmds.stop.toString(), robot!!)

}