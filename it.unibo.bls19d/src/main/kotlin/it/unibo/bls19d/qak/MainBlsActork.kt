package it.unibo.bls19d.qak

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking



class BlsActork{

    init{
        configure()
    }

    fun configure(){
        val led     = LedActork("led")
        blsActorMap.put(led.name, led )
        val control = ControlActork("control")
        blsActorMap.put(control.name, control )
        val button  = ButtonActork("btn", control.name )
        blsActorMap.put(button.name, button )

    }

    companion object{
        val blsActorMap : MutableMap<String, ActorBasic> =
            mutableMapOf<String, ActorBasic>()
    }
}

fun main() = runBlocking{
    println("START")
    BlsActork()
    println("       ENDS")
}


