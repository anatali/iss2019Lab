package it.unibo.kactor
import alice.tuprolog.*
import alice.tuprolog.Term.createTerm
import java.io.FileInputStream





/*
====================================================================
Package level functions
====================================================================
*/

object sysUtil{
	val pengine = Prolog()
	val ctxsMap :   MutableMap<String, QakContext> = mutableMapOf<String, QakContext>()
	val ctxActorMap :  MutableMap<String, ActorBasic> = mutableMapOf<String, ActorBasic>()

	fun getPrologEngine() : Prolog{
		return pengine
	}
	fun loadInfo(   ruleFile: String,   sysDescrFile: String) {
		loadTheory(ruleFile, pengine)
		loadTheory(sysDescrFile, pengine)
	}

	fun getActorContext( actorName : String): String?{
		val ctxName = solve( "qactor($actorName,CTX,_)", pengine, "CTX" )
		return ctxName
	}

	fun getAndCreatContexts(  ){
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

			getAndCreatActors( newctx )
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
 	}//getAndCreatConstexts

	fun getAndCreatActors( ctx: QakContext ){
		val actorNames : String? = solve("getActorNames(A,${ctx.name})", pengine, "A" )
		val actorList = actorNames!!.replace("[","").replace("]","").split(",")
		actorList.forEach{
			if( it.length > 0 ){
				val actorClass = solve("qactor($it,_,CLASS)", pengine, "CLASS")
				println("actor=$it in context: ${ctx.name}  class=$actorClass"   )
				val className = actorClass!!.replace("'","")
				val clazz = Class.forName(className)	//Class<?>
				val ctor  = clazz.getConstructor(String::class.java)  //Constructor<?>
				val actor = ctor.newInstance("$it") as ActorBasic
				ctx.addActor(actor)
				actor.context = ctx
				ctxActorMap.put("$it",actor  )
 				/*
				if( actorClass!!.contains("LedActor")   )  {
					val actor = it.unibo.bls19d.chain.led.LedActor("$it")  //actorClass
					ctx.addActor(actor)
					ctxActorMap.put("$it",actor)
				}else if( actorClass!!.contains("ControlActor")   )  {
					val actor = it.unibo.bls19d.chain.control.ControlActor("$it")  //actorClass
					ctx.addActor(actor)
					ctxActorMap.put("$it",actor)
				}
				*/
			}
		}
	}


}

fun solve( goal: String, pengine : Prolog, resVar: String  ) : String? {
	//println("StateMachine $name | solveGoal ${goal}" );
	val sol = pengine.solve( "$goal.")
	if( sol is SolveInfo ) {
		val result = sol.getVarValue(resVar)  //Term
		return  sol.getVarValue(resVar).toString()
	}
	else return null
}
fun solveT( goal: String, pengine : Prolog, resVar: String  ) : Term? {
	//println("StateMachine $name | solveGoal ${goal}" );
	val sol = pengine.solve( "$goal.")
	if( sol is SolveInfo ) {
		if( resVar.isNotEmpty()   ){
			val result = sol.getVarValue(resVar)  //Term
 			return  result
		}else{
 			return createTerm("ok")
		}
	}
	else return null
}

fun loadTheory(path: String,  pengine: Prolog) {
	try {
		val worldTh = Theory( FileInputStream(path) )
		pengine.addTheory(worldTh)
	} catch (e: Exception) {
		println("loadheory   |   WARNING: ${e}" );
	}
}



