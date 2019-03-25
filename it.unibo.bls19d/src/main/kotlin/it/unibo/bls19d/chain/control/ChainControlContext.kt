package it.unibo.bls19d.chain.control

import alice.tuprolog.Prolog
import it.unibo.bls19d.chain.led.LedActor
import it.unibo.kactor.*

/*
Create the subsystem on a given node (localhost)
 */
val portNum       = 8010

val ctxsMap :      MutableMap<String, QakContext> = mutableMapOf<String, QakContext>()
val ctxActorMap :  MutableMap<String, ActorBasic> = mutableMapOf<String, ActorBasic>()

fun getAndCreatConstexts( pengine: Prolog ){

    val ctxs : String? = solve("getCtxNames(X)", pengine, "X")
    //context( CTX, HOST, PROTOCOL, PORT )
    val ctxsList = ctxs!!.replace("[","").replace("]","").split(",")
    ctxsList?.forEach { ctx ->
        val ctxHost : String?     = solve("getCtxHost($ctx,H)", pengine, "H")
        val ctxProtocol : String? = solve("getCtxProtocol($ctx,P)", pengine, "P")
        val ctxPort : String?     = solve("getCtxPort($ctx,P)", pengine, "P")
        println("$ctx host=$ctxHost port = $ctxPort protocol=$ctxProtocol")
        val portNum = Integer.parseInt(ctxPort)

        val newctx = QakContext( "$ctx", "$ctxHost", portNum)
        ctxsMap.put("$ctx", newctx)

        getAndCreatActors(newctx, pengine )

    }//foreach ctx

    //Add proxy to each ctx
    ctxsList?.forEach { ctx ->
        val curCtx = ctxsMap.get("$ctx")
        val others = solve("getOtherContextNames(OTHERS,$ctx)", pengine, "OTHERS")
        val ctxs   = others!!.replace("[","").replace("]","").split(",")
        ctxs?.forEach {
            val ctxOther = ctxsMap.get("$it")
            ctxOther!!.addCtxProxy( curCtx!! )
        }
    }

    //SEND A CMD TO ControlActor
    //msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM )
    //val msg = ApplMessage("cmd", "dispatch", "main", "control","go","0")

    val main = ctxActorMap.get("control")

    main!!.forward("cmd","on",main)


}//getAndCreatConstexts

fun getAndCreatActors( ctx: QakContext, pengine: Prolog){
    val actorNames : String? = solve("getActorNames(A,${ctx.name})", pengine, "A" )
    val actorList = actorNames!!.replace("[","").replace("]","").split(",")
    actorList.forEach{
          if( it.length > 0 ){
              val actorClass = solve("qactor($it,_,CLASS)", pengine, "CLASS")
              println("actor=$it in context: ${ctx.name}  class=$actorClass"   )
              if( actorClass!!.contains("LedActor")   )  {
                  val actor = LedActor("$it")  //actorClass
                  ctx.addActor(actor)
                  ctxActorMap.put("$it",actor)
              }else if( actorClass!!.contains("ControlActor")   )  {
                  val actor = ControlActor("$it")  //actorClass
                  ctx.addActor(actor)
                  ctxActorMap.put("$it",actor)
              }
          }
    }
}

fun main() {
    val numOfContexts = 1
    val numOfLeds     = 3
    val pengine = Prolog()

    loadTheory("src\\main\\kotlin\\it\\unibo\\bls19d\\chain\\control\\sysRules.pl", pengine)
    loadTheory("src/main/kotlin/it/unibo/bls19d/chain/control/sysDescr.pl", pengine)

    getAndCreatConstexts(pengine)

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