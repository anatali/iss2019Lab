package it.unibo.kactor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class QakContext(name: String, val hostAddr: String, val portNum: Int,
                      val gui : Boolean = false ) : ActorBasic(name){

    internal val actorMap : MutableMap<String, ActorBasic> = mutableMapOf<String, ActorBasic>()
    internal val proxyMap:  MutableMap<String, NodeProxy> = mutableMapOf<String, NodeProxy>()  //cannot be static

    companion object {
        val workTime = 600000L
        enum class CtxMsg { attach, remove }

        fun getActor( actorName : String ) : ActorBasic? {
            return sysUtil.getActor(actorName)
        }

        suspend fun createContexts(hostName: String, scope: CoroutineScope,
                           desrFilePath: String, rulesFilePath: String ) {
            sysUtil.createContexts(hostName, desrFilePath, rulesFilePath)
            println("QakContext CREATING THE ACTORS on $hostName ")
            sysUtil.ctxOnHost.forEach { ctx -> sysUtil.createTheActors(ctx)  }
            //Avoid premature termination
            scope.launch{
                 println("QakContexts on $hostName CREATED. I will terminate after $workTime msec")
                delay( workTime )
            }
        }

    }

    init{
        println("QakContext $name | INIT on port=$portNum CREATES the QakContextServer gui=$gui")
        if( gui ){
        }
        QakContextServer( this, "server$name", Protocol.TCP )
    }

    override suspend fun actorBody(msg : ApplMessage){
        println("QakContext $name |  receives $msg " )
    }

    fun addCtxProxy(ctxName: String, protocol: String, hostAddr: String, portNumStr: String) {
        val p = MsgUtil.strToProtocol(protocol)
        val portNum = Integer.parseInt(portNumStr)
        println("QakContext | addCtxProxy ${ctxName}, $hostAddr, $portNum")
        val proxy = NodeProxy("proxy${ctxName}", p, hostAddr, portNum)
        proxyMap.put(ctxName, proxy)
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