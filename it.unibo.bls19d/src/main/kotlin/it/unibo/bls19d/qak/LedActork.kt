package it.unibo.bls19d.qak

import it.unibo.kactor.*

class LedActork( name : String ) : ActorBasic( name ){
    val concreteLed = LedSegm()

    init{
        SystemKb.blsActorMap.put( name, this )
    }

    override suspend fun actorBody(msg : ApplMessage){
        //println("   LedActork $name |  msg= $msg "  )
        when( msg.msgContent() ){
            "ledCmd(on)"  -> concreteLed.turnOn()
            "ledCmd(off)" -> concreteLed.turnOff()
            else -> println("   it.unibo.bls19d.qak.LedActork $name | UNKNOWN $msg")
        }
    }
}