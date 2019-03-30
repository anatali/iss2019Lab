package it.unibo.bls19d.qak.distr

import it.unibo.bls19d.qak.ControlActork
import it.unibo.bls19d.qak.LedActork
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

class BlsDistrNode2{
    companion object{
        val blsActorMap : MutableMap<String, ActorBasic> =
            mutableMapOf<String, ActorBasic>()
    }

    init{
        configure()
    }

    fun configure(){
        val led     = LedActork("led")
        blsActorMap.put(led.name, led )
        val  control = ControlActork("control")
        blsActorMap.put(control.name, control )
        val servercontrol = ServerControl("serverControl")
        blsActorMap.put(servercontrol.name, servercontrol )
     }

 }

fun main() = runBlocking{
    println("BlsDistrNode2 | START ${sysUtil.curThread()}")
    BlsDistrNode2()
    println("BlsDistrNode2 | ENDS")
}


