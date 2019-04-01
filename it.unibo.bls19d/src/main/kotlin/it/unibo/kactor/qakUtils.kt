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
	val ctxsMap :      MutableMap<String, QakContext> = mutableMapOf<String, QakContext>()
	val ctxActorMap :  MutableMap<String, ActorBasic> = mutableMapOf<String, ActorBasic>()

	fun getPrologEngine() : Prolog = pengine
	fun curThread() : String = "thread=${Thread.currentThread().name}"

	fun loadInfo(   ruleFile: String,   sysDescrFile: String) {
		loadTheory(ruleFile, pengine)
		loadTheory(sysDescrFile, pengine)
	}

	fun getActorContext( actorName : String): String?{
		val ctxName = solve( "qactor($actorName,CTX,_)", pengine, "CTX" )
		return ctxName
	}
	fun createContexts(  hostName : String  ){
		val ctxs : String? = solve("getCtxNames(X)", pengine, "X")
		//context( CTX, HOST, PROTOCOL, PORT )
		val ctxsList = ctxs!!.replace("[","")
			.replace("]","").split(",")
		ctxsList?.forEach { ctx ->
			val newctx = createTheContext(ctx, hostName = hostName)
			if( newctx is QakContext) createTheActors( newctx )
		}//foreach ctx
		addProxyToOtherCtxs( ctxsList )
	}//createContexts

	fun createTheContext(  ctx : String, hostName : String  ) : QakContext?{
		val ctxHost : String?     = solve("getCtxHost($ctx,H)", pengine, "H")
		if( ! ctxHost.equals(hostName) ) return null
		val ctxProtocol : String? = solve("getCtxProtocol($ctx,P)", pengine, "P")
		val ctxPort : String?     = solve("getCtxPort($ctx,P)", pengine, "P")
		println("$ctx host=$ctxHost port = $ctxPort protocol=$ctxProtocol")
		val portNum = Integer.parseInt(ctxPort)
		//CREATE AND MEMO THE CONTEXT
		val newctx = QakContext( "$ctx", "$ctxHost", portNum)
		ctxsMap.put("$ctx", newctx)
		return newctx
 	}//createTheContext

	fun addProxyToOtherCtxs( ctxsList : List<String>){
		ctxsList?.forEach { ctx ->
			val curCtx = ctxsMap.get("$ctx")
			if( curCtx is QakContext ) {
				val others = solve("getOtherContextNames(OTHERS,$ctx)", pengine, "OTHERS")
				val ctxs = others!!.replace("[", "")
					.replace("]", "").split(",")
				ctxs?.forEach {
					val ctxOther = ctxsMap.get("$it")
					//if (ctxOther is QakContext) {
						//println("FOR ACTIVATED CONTEXT ${ctxOther!!.name}: ADDING A PROXY to ${curCtx!!.name} ")
						ctxOther?.addCtxProxy(curCtx!!)
					//}else{
					//	println("WARNING: CONTEXT $it NOT ACTIVATED: " +
					//			"WE SHOULD WAIT FOR IT, TO SET THE PROXY in ${curCtx!!.name}")
					//}
				}
			}else{ println("WARNING: $ctx NOT ACTIVATED ") }
		}
	}//addProxyToOtherCtxs

	fun createTheActors( ctx: QakContext ){
		val actorNames : String? = solve("getActorNames(A,${ctx.name})", pengine, "A" )
		val actorList = actorNames!!.replace("[","").replace("]","").split(",")
		actorList.forEach{
			if( it.length > 0 ){
				val actorClass = solve("qactor($it,_,CLASS)", pengine, "CLASS")
				println("CREATE actor=$it in context: ${ctx.name}  class=$actorClass"   )
				val className = actorClass!!.replace("'","")
				val clazz = Class.forName(className)	//Class<?>
				val ctor  = clazz.getConstructor(String::class.java)  //Constructor<?>
				val actor = ctor.newInstance("$it") as ActorBasic
				ctx.addActor(actor)
				actor.context = ctx
				//MEMO THE ACTOR
				ctxActorMap.put("$it",actor  )
			}
		}
	}//createTheActors

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



