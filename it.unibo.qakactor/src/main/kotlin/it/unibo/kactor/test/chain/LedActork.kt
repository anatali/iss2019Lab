package it.unibo.kactor.test.chain

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage

class LedActork( name : String ) : ActorBasic( name ){
    val concreteLed = LedSegm()

    override suspend fun actorBody(msg : ApplMessage){
        //println("   LedActork $name |  msg= $msg "  )
        when( msg.msgContent() ){
            "ledCmd(on)"  -> concreteLed.turnOn()
            "ledCmd(off)" -> concreteLed.turnOff()
            else -> println("   LedActork $name | UNKNOWN $msg")
        }
    }
}