package it.unibo.bls19d.qak.distr

import it.unibo.bls19d.qak.ButtonActork
import it.unibo.bls19d.qak.SystemKb
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.Protocol
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

class BlsDistrNode1{

    init{
        configure()
    }

    fun configure(){
         val proxyControl = ProxyControl(
             "proxyControl", Protocol.TCP, "localhost", SystemKb.portNumber)
        SystemKb.blsActorMap.put( proxyControl.name, proxyControl )
        val button  = ButtonActork("button", proxyControl.name )
        SystemKb.blsActorMap.put(button.name, button )
    }

 }

fun main() = runBlocking{
    println("BlsDistrNode1 | START ${sysUtil.curThread()}")
    BlsDistrNode1()
    println("BlsDistrNode1 | ENDS")
}


