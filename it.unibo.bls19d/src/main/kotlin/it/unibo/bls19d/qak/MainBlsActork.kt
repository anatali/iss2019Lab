package it.unibo.bls19d.qak

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


val blsActorMap : MutableMap<String, ActorBasic> =
    mutableMapOf<String, ActorBasic>()

fun main() = runBlocking{
    println("START")
    val led     = LedActork("led")
    blsActorMap.put(led.name, led )
    val control = ControlActork("control")
    blsActorMap.put(control.name, control )
    val button  = ButtonActork("btn")
    blsActorMap.put(button.name, button )



    //SIMULATION: the user should use a GUI
    /*
    val msg = BlsCmd.ButtonCmd()
    for(  i in 1..3 ){
        println("       CLICK $i")
        MsgUtil.sendMsg("${msg.id}", "${msg.toString()}", button)
        delay(2000)
     }
     */
    //delay(3000)
    println("       ENDS")
}


