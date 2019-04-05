package it.unibo.robot19

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage

class BasicRobotExecutor( name : String ) : ActorBasic(name){

    override suspend fun actorBody(msg: ApplMessage){
        println("BasicRobotExecutor | receives $msg   ")
    }

}