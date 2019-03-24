package it.unibo.bls19d.chain.control

import alice.tuprolog.Prolog
import alice.tuprolog.Struct
import alice.tuprolog.Term
import it.unibo.bls19d.chain.elements.LedComponent
import it.unibo.kactor.QakContext
import it.unibo.kactor.loadTheory
import it.unibo.kactor.solve
import it.unibo.kactor.solveT


fun main() {
    val numOfContexts = 1
    val numOfLeds     = 3
    val portNum       = 8010
    val pengine = Prolog()

    loadTheory("src\\main\\kotlin\\it\\unibo\\bls19d\\chain\\control\\sysRules.pl", pengine)
    loadTheory("src\\main\\kotlin\\it\\unibo\\bls19d\\chain\\control\\sysDescr.pl", pengine)

    val ctxNames : Term? = solveT("getCtxNames(X)", pengine, "X")

    println("$ctxNames")
    solveT("showListOfElements($ctxNames)", pengine, "")
    //ctxNames?.forEach {

        //val actorNames : Term? = solveT("getTheActors(A,'')", pengine, "A" )
        //val a = actorNames as Struct
        //solve("showListOfElements($actorNames)", pengine, "")
        //println("a=$a"  )
    //}

    /*
    val mainCtx = QakContext("ctxControl", "localhost", portNum )

    val port = portNum+10
    val ctx  = QakContext( "ctxLeds", "localhost", port)
    for(i in 1..numOfLeds){
        val led  = LedComponent("led$i", 100+i*120)
        ctx.addActor( led.getActor() )
    }

    //Add proxy
    ctx.addCtxProxy( mainCtx )
    mainCtx.addCtxProxy( ctx )
*/
    //mainCtx.addActor( )
}