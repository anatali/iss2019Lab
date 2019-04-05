package it.unibo.bls19d.qak

import kotlinx.coroutines.runBlocking

class BlsActork{
    init{
        configure()
    }
    fun configure(){
        val led     = LedActork("led")
        SystemKb.blsActorMap.put(led.name, led )
        val control = ControlActork("control", led.name )
        SystemKb.blsActorMap.put(control.name, control )
        val button  = ButtonActork("button", control.name )
        SystemKb.blsActorMap.put(button.name, button )
    }
 }
fun main() = runBlocking{
    println("START")
    BlsActork()
    println("       ENDS")
}


