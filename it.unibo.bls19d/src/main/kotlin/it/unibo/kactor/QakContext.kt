package it.unibo.kactor

import alice.tuprolog.Prolog

open class QakContext(name: String, val hostAddr: String, val portNum: Int ) : ActorBasic(name){
    val pengine = Prolog()

    companion object {
        enum class CtxMsg{
            attach, remove
        }
    }

    protected val actorMap : MutableMap<String, ActorBasic> =
                                            mutableMapOf<String, ActorBasic>()
    val proxyMap : MutableMap<String, NodeProxy> =
                                            mutableMapOf<String, NodeProxy>()
    init{
        println("QakContext $name INIT on port=$portNum")
        QakContextServer( this, "Node$name", Protocol.TCP )
    }

    override suspend fun actorBody(msg : ApplMessage){
        println("       QakContext $name receives $msg " )
    }


    fun addActor( actor: ActorBasic ) {
        actorMap.put( actor.name, actor )
    }

    fun hasActor( actorName: String ) : Boolean {
        return actorMap.containsKey(actorName)
    }

    fun addCtxProxy( ctx : QakContext ){
        val proxy = NodeProxy("proxy${ctx.name}", Protocol.TCP, ctx.hostAddr, ctx.portNum)
        proxyMap.put( ctx.name, proxy )
    }

    fun addCtxProxy( ctxName :String, hostAddr: String, portNum : Int  ){
        val proxy = NodeProxy("proxy${ctxName}", Protocol.TCP, hostAddr, portNum)
        proxyMap.put( ctxName, proxy )
    }

}