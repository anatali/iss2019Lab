package it.unibo.bls19d.qak

import it.unibo.kactor.*

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