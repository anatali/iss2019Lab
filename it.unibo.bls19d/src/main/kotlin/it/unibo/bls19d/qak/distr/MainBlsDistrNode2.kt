package it.unibo.bls19d.qak.distr

import it.unibo.bls19d.qak.ControlActork
import it.unibo.bls19d.qak.LedActork
import it.unibo.bls19d.qak.SystemKb
import it.unibo.kactor.Protocol
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

class BlsDistrNode2{

    init{
        configure()
    }

    fun configure(){
        val led     = LedActork("led")
        SystemKb.blsActorMap.put(led.name, led )
        val  control = ControlActork("control", led.name)
        SystemKb.blsActorMap.put(control.name, control )
        val servercontrol = ServerControl(
            "serverControl", Protocol.TCP, SystemKb.portNumber, control.name )
        SystemKb.blsActorMap.put(servercontrol.name, servercontrol )
     }

 }

fun main() = runBlocking{
    println("BlsDistrNode2 | START ${sysUtil.curThread()}")
    BlsDistrNode2()
    println("BlsDistrNode2 | ENDS")
}


