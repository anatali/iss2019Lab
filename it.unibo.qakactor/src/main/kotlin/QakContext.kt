package it.unibo.kactor

import javafx.application.Application.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class QakContext(name: String, val hostAddr: String, val portNum: Int ) : ActorBasic(name){
    //val pengine = Prolog()

    protected val actorMap : MutableMap<String, ActorBasic> =
        mutableMapOf<String, ActorBasic>()
    //val proxyMap : MutableMap<String, NodeProxy> = mutableMapOf<String, NodeProxy>()

    companion object {
        val proxyMap: MutableMap<String, NodeProxy> = mutableMapOf<String, NodeProxy>()
        val workTime = 600000L
        enum class CtxMsg { attach, remove }

        fun addCtxProxy(ctxName: String, protocol: String, hostAddr: String, portNumStr: String) {
            val p = MsgUtil.strToProtocol(protocol)
            val portNum = Integer.parseInt(portNumStr)
            println("QakContext | addCtxProxy ${ctxName}, $hostAddr, $portNum")
            val proxy = NodeProxy("proxy${ctxName}", p, hostAddr, portNum)
            proxyMap.put(ctxName, proxy)
        }

        fun getActor( actorName : String ) : ActorBasic? {
            return sysUtil.getActor(actorName)
        }

        suspend fun createContexts(hostName: String, scope: CoroutineScope,
                           desrFilePath: String, rulesFilePath: String ) {
            //val job = scope.launch {
                sysUtil.createContexts(hostName, desrFilePath, rulesFilePath)
            //}
            //job.join();
            println("QakContext CREATING THE ACTORS on $hostName ")
            sysUtil.ctxOnHost.forEach { ctx -> sysUtil.createTheActors(ctx)  }//foreach ctx

            scope.launch{
                 println("QakContexts on $hostName CREATED. I will terminate after $workTime msec")
                delay( workTime )
            }

            //return job
        }

    }

    init{
        println("QakContext $name | INIT on port=$portNum CREATES the QakContextServer")
        QakContextServer( this, "server$name", Protocol.TCP )
    }

    override suspend fun actorBody(msg : ApplMessage){
        println("QakContext $name |  receives $msg " )
    }

    fun addActor( actor: ActorBasic ) {
        actor.context = this
        actorMap.put( actor.name, actor )
        //println("QakContext $name | addActor ${actor.name}")
    }


    fun hasActor( actorName: String ) : ActorBasic? {
        return actorMap.get(actorName)
    }

    fun addCtxProxy( ctx : QakContext ){
        println("QakContext $name | addCtxProxy ${ctx.name}")
        val proxy = NodeProxy("proxy${ctx.name}", Protocol.TCP, ctx.hostAddr, ctx.portNum)
        proxyMap.put( ctx.name, proxy )
    }

    fun addCtxProxy( ctxName :String, hostAddr: String, portNum : Int  ){
        val proxy = NodeProxy("proxy${ctxName}", Protocol.TCP, hostAddr, portNum)
        proxyMap.put( ctxName, proxy )
    }

}