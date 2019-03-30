package it.unibo.bls19d.qak

import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.runBlocking

class BlsActork{
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
        val control = ControlActork("control")
        blsActorMap.put(control.name, control )
        val button  = ButtonActork("button", control.name )
        blsActorMap.put(button.name, button )
    }

 }

fun main() = runBlocking{
    println("START")
    BlsActork()
    println("       ENDS")
}


