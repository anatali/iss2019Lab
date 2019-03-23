package it.unibo.bls19d.chain.control

import it.unibo.bls19d.chain.elements.LedComponent
import it.unibo.kactor.NodeContext


fun main() {
    val numOfContexts = 1
    val numOfLeds     = 3
    val portNum       = 8010

    val mainCtx = NodeContext("ctxControl", "localhost", portNum )

    val port = portNum+10
    val ctx  = NodeContext( "ctxLeds", "localhost", port)
    for(i in 1..numOfLeds){
        val led  = LedComponent("led$i", 100+i*120)
        ctx.addActor( led.getActor() )
    }

    //Add proxy
    ctx.addCtxProxy( mainCtx )
    mainCtx.addCtxProxy( ctx )

    //mainCtx.addActor( )
}