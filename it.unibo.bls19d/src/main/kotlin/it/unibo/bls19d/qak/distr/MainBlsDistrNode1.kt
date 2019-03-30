package it.unibo.bls19d.qak.distr

import it.unibo.bls19d.qak.ButtonActork
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.Protocol
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

class BlsDistrNode1{
    companion object{
        val blsActorMap : MutableMap<String, ActorBasic> =
            mutableMapOf<String, ActorBasic>()
    }

    init{
        configure()
    }

    fun configure(){
         val proxyControl = ProxyControl("proxyControl", Protocol.TCP, "localhost", 8022)
        blsActorMap.put(proxyControl.name, proxyControl )
        val button  = ButtonActork("button", proxyControl.name )
        blsActorMap.put(button.name, button )
    }

 }

fun main() = runBlocking{
    println("BlsDistrNode1 | START ${sysUtil.curThread()}")
    BlsDistrNode1()
    println("BlsDistrNode1 | ENDS")
}


