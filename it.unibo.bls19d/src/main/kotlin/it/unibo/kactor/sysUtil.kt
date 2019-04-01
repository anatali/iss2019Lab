package it.unibo.kactor
import alice.tuprolog.*
import java.io.FileInputStream

object sysUtil{
	val pengine = Prolog()
	val ctxsMap :      MutableMap<String, QakContext> = mutableMapOf<String, QakContext>()
	val ctxActorMap :  MutableMap<String, ActorBasic> = mutableMapOf<String, ActorBasic>()

	fun getPrologEngine() : Prolog = pengine
	fun curThread() : String = "thread=${Thread.currentThread().name}"

	fun loadInfo(   sysDescrFile: String) {
		loadTheory( "src/main/kotlin/it/unibo/kactor/sysRules.pl" )
		loadTheory( sysDescrFile )
	}

	fun getActorContext( actorName : String): String?{
		val ctxName = solve( "qactor($actorName,CTX,_)", "CTX" )
		return ctxName
	}
	fun createContexts(  hostName : String  ){
		val ctxs : String? = solve("getCtxNames(X)","X")
		//context( CTX, HOST, PROTOCOL, PORT )
		val ctxsList = strRepToList(ctxs!!)
			//ctxs!!.replace("[","") .replace("]","").split(",")
		ctxsList?.forEach { ctx ->
			val newctx = createTheContext(ctx, hostName = hostName)
			if( newctx is QakContext) createTheActors( newctx )
		}//foreach ctx
		addProxyToOtherCtxs( ctxsList )
	}//createContexts

	fun createTheContext(  ctx : String, hostName : String  ) : QakContext?{
		println("sysUtil | $ctx host=$hostName  ")
		val ctxHost : String?  = solve("getCtxHost($ctx,H)","H")
		println("sysUtil | ctxHost=$ctxHost  ")
		if( ! ctxHost.equals(hostName) ) return null
		val ctxProtocol : String? = solve("getCtxProtocol($ctx,P)","P")
		val ctxPort : String?     = solve("getCtxPort($ctx,P)","P")
		println("sysUtil | $ctx host=$ctxHost port = $ctxPort protocol=$ctxProtocol")
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
				val others = solve("getOtherContextNames(OTHERS,$ctx)","OTHERS")
				val ctxs = strRepToList(others!!)
					//others!!.replace("[", "").replace("]", "").split(",")
				ctxs?.forEach {
					val ctxOther = ctxsMap.get("$it")
					if (ctxOther is QakContext) {
						//println("FOR ACTIVATED CONTEXT ${ctxOther!!.name}: ADDING A PROXY to ${curCtx!!.name} ")
						ctxOther?.addCtxProxy(curCtx!!)
					}else{
						println("sysUtil | WARNING: CONTEXT ${it} NOT ACTIVATED: " +
					 			"WE SHOULD WAIT FOR IT, TO SET THE PROXY in ${curCtx!!.name}")
						val ctxHost : String?     = solve("getCtxHost($it,H)","H")
 						val ctxProtocol : String? = solve("getCtxProtocol($it,P)","P")
						val ctxPort : String?     = solve("getCtxPort($it,P)","P")
						QakContext.addCtxProxy( it, ctxProtocol!!, ctxHost!!, ctxPort!! )
					}
				}
			}else{ println("sysUtil | WARNING: $ctx NOT ACTIVATED ") }
		}
	}//addProxyToOtherCtxs

	fun getAllActorNames(ctxName: String) : List<String>{
		val actorNames : String? = solve("getActorNames(A,$ctxName)","A" )
		val actorList = strRepToList(actorNames!!)
		return actorList
	}

	fun strRepToList( liststrRep: String ) : List<String>{
		return liststrRep.replace("[","")
			.replace("]","").split(",")
	}
	fun createTheActors( ctx: QakContext ){
		//val actorNames : String? = solve("getActorNames(A,${ctx.name})","A" )
		//val actorList = actorNames!!.replace("[","").replace("]","").split(",")
		val actorList = getAllActorNames(ctx.name)
		println("sysUtil | createTheActors actorList=$actorList "   )
		actorList.forEach{
			if( it.length > 0 ){
				val actorClass = solve("qactor($it,_,CLASS)","CLASS")
				println("sysUtil | CREATE actor=$it in context:${ctx.name}  class=$actorClass"   )
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

	fun solve( goal: String, resVar: String  ) : String? {
		//println("sysUtil $name | solveGoal ${goal}" );
		val sol = pengine.solve( "$goal.")
		if( sol is SolveInfo ) {
			val result = sol.getVarValue(resVar)  //Term
			var resStr = sol.getVarValue(resVar).toString()
			return  strCleaned( resStr )
		}
		else return null
	}

	fun loadTheory( path: String ) {
		try {
			val worldTh = Theory( FileInputStream(path) )
			pengine.addTheory(worldTh)
		} catch (e: Exception) {
			println("sysUtil | loadheory WARNING: ${e}" );
		}
	}

	fun strCleaned( s : String) : String{
		if( s.startsWith("'")) return s.replace("'","")
		else return s

	}
}//sysUtil
