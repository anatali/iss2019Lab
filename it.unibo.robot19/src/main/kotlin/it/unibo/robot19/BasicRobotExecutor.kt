package it.unibo.robot19

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class BasicRobotExecutor( name : String ) : ActorBasic(name){
    init{
        clientWenvTcp.initClientConn()
    }
    override suspend fun actorBody(msg: ApplMessage){
        println("BasicRobotExecutor | receives $msg   ")
        when( msg.msgId() ){    //robotCmd
            RobotCmds.id ->
                when( msg.msgContent()  ) {
                    RobotCmds.forward.toString() -> //robotCmd(forward)
                    { clientWenvTcp.sendMsg(RobotCmds.cmdMap.get(RobotCmds.forwardStr)!!) }
                    RobotCmds.backward.toString() ->
                    { clientWenvTcp.sendMsg(RobotCmds.cmdMap.get(RobotCmds.backwardStr)!!) }
                    RobotCmds.stop.toString() ->
                    { clientWenvTcp.sendMsg(RobotCmds.cmdMap.get(RobotCmds.stopStr)!!) }
                    RobotCmds.turnLeft.toString() ->
                    { clientWenvTcp.sendMsg(RobotCmds.cmdMap.get(RobotCmds.turnLeftStr)!!) }
                    RobotCmds.turnRight.toString() ->
                    { clientWenvTcp.sendMsg(RobotCmds.cmdMap.get(RobotCmds.turnRightStr)!!) }
                    else -> println("BasicRobotExecutor | msg content UNKNOWN")
                }
            else -> println("BasicRobotExecutor | msg id UNKNOWN")
        }
    }
}
//Rapide check
fun main() = runBlocking{
    println("START   ")
    val a = BasicRobotExecutor("robotExecutor")
    MsgUtil.sendMsg(RobotCmds.id, RobotCmds.turnLeft.toString(), a)
    delay( 1000 )
    MsgUtil.sendMsg(RobotCmds.id, RobotCmds.turnRight.toString(), a)
    delay( 1000 )
    MsgUtil.sendMsg(RobotCmds.id, RobotCmds.forward.toString(), a)
    delay( 1000 )
    MsgUtil.sendMsg(RobotCmds.id, RobotCmds.backward.toString(), a)
    delay( 1000 )
    MsgUtil.sendMsg(RobotCmds.id, RobotCmds.stop.toString(), a)
}