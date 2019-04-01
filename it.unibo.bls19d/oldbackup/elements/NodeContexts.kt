package it.unibo.bls19d.chain.elements

import it.unibo.bls19d.chain.led.LedActor
import it.unibo.bls19d.chain.led.LedProxy
import it.unibo.kactor.Protocol
import it.unibo.kactor.*


/*
Builds numOfElements
 */
class NodeContexts(  ){

    companion object{
          val numOfContexts  = 1
          val numOfLeds      = 3
    }

    val protocol  = Protocol.TCP
    val portNum   = 8000
    val ledActorList : ArrayList<LedActor> = arrayListOf<LedActor>()
    val proxyList    : ArrayList<LedProxy> = arrayListOf<LedProxy>()

    init{
        createContexts(   )
    }

    protected fun createContexts(  ){
        for( i in 1..numOfContexts ){
            val port = portNum+i*10
            val ctx  = QakContext( "ctx$i", "localhost", port)
            for(i in 1..numOfLeds ){
                val led  = LedComponent("_$i", 100+i*120)
                ctx.addActor( led.getActor() )
            }
        }
    }
}

//Rapid check
fun main() {
    val elements = NodeContexts( )
}