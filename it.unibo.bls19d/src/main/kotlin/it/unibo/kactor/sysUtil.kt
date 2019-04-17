package it.unibo.kactor
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.newSingleThreadContext
import java.io.FileInputStream

//A module in kotlin is a set of Kotlin files compiled together
object sysUtil{
	private val pengine = Prolog()
	internal val ctxsMap :      MutableMap<String, QakContext> = mutableMapOf<String, QakContext>()
	internal val ctxActorMap :  MutableMap<String, ActorBasic> = mutableMapOf<String, ActorBasic>()
	val ctxOnHost =  mutableListOf<QakContext>()

	val runtimeEnvironment     = Runtime.getRuntime()

	val cpus                   = Runtime.getRuntime().availableProcessors()
	val singleThreadContext    = newSingleThreadContext("qaksingle")
	val ioBoundThreadContext   = newFixedThreadPoolContext(64, "qakiopool")
	val cpusThreadContext      = newFixedThreadPoolContext(cpus, "qakcpuspool")

	var mqttBrokerIP : String? = ""
	var mqttBrokerPort : String? = ""

	fun getPrologEngine() : Prolog = pengine
	fun curThread() : String = "thread=${Thread.currentThread().name}"

	fun getContext( ctxName : String ) : QakContext?  { return ctxsMap.get(ctxName)}
	fun getActor( actorName : String ) : ActorBasic? { return ctxActorMap.get(actorName)}

	fun getActorContextName( actorName : String): String?{
		val ctxName = solve( "qactor($actorName,CTX,_)", "CTX" )
		return ctxName
	}
	fun getActorContext ( actorName : String): QakContext?{
		val ctxName = solve( "qactor($actorName,CTX,_)", "CTX" )
		//println("       sysUtil | getActorContext ctxName=${ctxName} - ${ctxsMap.get( ctxName )}")
		return ctxsMap.get( ctxName )
	}
	fun createContexts(  hostName : String,
					desrFilePath:String, rulesFilePath:String){
		loadTheory( desrFilePath )
		loadTheory( rulesFilePath )

		try {
			mqttBrokerIP = solve("mqttBroker(IP,_)", "IP")
			mqttBrokerPort = solve("mqttBroker(_,PORT)", "PORT")
		}catch(e: Exception){
			println("sysUtil | NO MQTT borker FOUND")
		}
			val ctxs: String? = solve("getCtxNames(X)", "X")
			//context( CTX, HOST, PROTOCOL, PORT )
			val ctxsList = strRepToList(ctxs!!)
			//waits for all the other context before activating the actors
			ctxsList.forEach { ctx -> createTheContext(ctx, hostName = hostName) }//foreach ctx
			addProxyToOtherCtxs(ctxsList)  //here could wait in polling ...
	}//createContexts

	fun createTheContext(  ctx : String, hostName : String  ) : QakContext?{
		//println("sysUtil | $ctx host=$hostName  ")
		val ctxHost : String?  = solve("getCtxHost($ctx,H)","H")
		//println("sysUtil | createTheContext $ctx ctxHost=$ctxHost  ")
		val ctxProtocol : String? = solve("getCtxProtocol($ctx,P)","P")
		val ctxPort     : String? = solve("getCtxPort($ctx,P)","P")
		//println("sysUtil | $ctx host=$ctxHost port = $ctxPort protocol=$ctxProtocol")
		val portNum = Integer.parseInt(ctxPort)

		val useMqtt = ctxProtocol!!.toLowerCase() == "mqtt"
		var mqttAddr = ""
		if( useMqtt ){
			println("sysUtil | context $ctx WORKS WITH MQTT")
			if( mqttBrokerIP != null ) mqttAddr = "tcp://$mqttBrokerIP:$mqttBrokerPort"
			else{ throw Exception("no MQTT broker declared")  }
		}
		//CREATE AND MEMO THE CONTEXT
		val newctx = QakContext( ctx, "$ctxHost", portNum, "") //isa ActorBasic
		newctx.mqttAddr = mqttAddr //!!!!!! INJECTION !!!!!!
		ctxsMap.put(ctx, newctx)
		if( ! ctxHost.equals(hostName) ){
			return null
		}
		ctxOnHost.add(newctx)
		return newctx
 	}//createTheContext

	fun addProxyToOtherCtxs( ctxsList : List<String>){
		ctxsList.forEach { ctx ->
			val curCtx = ctxsMap.get("$ctx")
			if( curCtx is QakContext ) {
				val others = solve("getOtherContextNames(OTHERS,$ctx)","OTHERS")
				val ctxs = strRepToList(others!!)
 					//others!!.replace("[", "").replace("]", "").split(",")
				ctxs.forEach {
 					if( it.length==0  ) return
					val ctxOther = ctxsMap.get("$it")
					if (ctxOther is QakContext) {
						//println("FOR ACTIVATED CONTEXT ${ctxOther!!.name}: ADDING A PROXY to ${curCtx!!.name} ")
						ctxOther.addCtxProxy(curCtx)
					}else{
						if( ctxOther!!.mqttAddr.length > 1 )  return //NO PROXY for MQTT ctx
						println("sysUtil | WARNING: CONTEXT ${it} NOT ACTIVATED: " +
					 			"WE SHOULD WAIT FOR IT, TO SET THE PROXY in ${curCtx.name}")
						val ctxHost : String?     = solve("getCtxHost($it,H)","H")
 						val ctxProtocol : String? = solve("getCtxProtocol($it,P)","P")
						val ctxPort : String?     = solve("getCtxPort($it,P)","P")
						curCtx.addCtxProxy( it, ctxProtocol!!, ctxHost!!, ctxPort!! )
					}
				}
			} //else{ println("sysUtil | WARNING: $ctx NOT ACTIVATED ") }
		}
	}//addProxyToOtherCtxs

	fun getAllActorNames(ctxName: String) : List<String>{
		val actorNames : String? = solve("getActorNames(A,$ctxName)","A" )
		val actorList = strRepToList(actorNames!!)
		return actorList
	}
	fun getAllActorNames( ) : List<String>{
		val actorNames : String? = solve("getActorNames(A,ANY)","A" )
		val actorList = strRepToList(actorNames!!)
		return actorList
	}

	fun strRepToList( liststrRep: String ) : List<String>{
		return liststrRep.replace("[","")
			.replace("]","").split(",")
	}
	fun createTheActors( ctx: QakContext ){
		val actorList = getAllActorNames(ctx.name)
		//println("sysUtil | createTheActors ${ctx.name} actorList=$actorList "   )
		actorList.forEach{
			if( it.length > 0 ){
				val actorClass = solve("qactor($it,_,CLASS)","CLASS")
				//println("sysUtil | CREATE actor=$it in context:${ctx.name}  class=$actorClass"   )
				val className = actorClass!!.replace("'","")
				createActor( ctx, it, className)
			}
		}
	}//createTheActors

	fun createActor( ctx: QakContext, actorName: String, className : String  ){
		println("sysUtil | CREATE actor=$actorName in context:${ctx.name}  class=$className"   )
		val clazz = Class.forName(className)	//Class<?>
 		val ctor  = clazz.getConstructor(String::class.java, CoroutineScope::class.java )  //Constructor<?>
		val actor = ctor.newInstance(actorName, GlobalScope ) as ActorBasic
		ctx.addActor(actor)
		actor.context = ctx
		//MEMO THE ACTOR
		ctxActorMap.put(actorName,actor  )
	}

	fun solve( goal: String, resVar: String  ) : String? {
		//println("sysUtil $name | solveGoal ${goal}" );
		val sol = pengine.solve( "$goal.")
		if( sol is SolveInfo ) {
			val result = sol.getVarValue(resVar)  //Term
			var resStr = result.toString()
			return  strCleaned( resStr )
		}
		else return null
	}

	fun loadTheory( path: String ) {
		try {
			//user.dir is typically the directory in which the Java virtual machine was invoked.
			//val executionPath = System.getProperty("user.dir")
			//println("sysUtil | loadheory executionPath: ${executionPath}" )
			//val resource = classLoader.getResource("/") //URL
			//val cl =  javaClass<ActorBasic> //javaClass does not work
			//println("sysUtil | loadheory classloader: ${cl}" )
			val worldTh = Theory( FileInputStream(path) )
			pengine.addTheory(worldTh)
		} catch (e: Exception) {
			println("sysUtil | loadheory WARNING: ${e}" )
			throw e
		}
	}


	fun strCleaned( s : String) : String{
		if( s.startsWith("'")) return s.replace("'","")
		else return s

	}
}//sysUtil
