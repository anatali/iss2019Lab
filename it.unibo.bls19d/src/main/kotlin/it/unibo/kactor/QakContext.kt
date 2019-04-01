package it.unibo.kactor

open class QakContext(name: String, val hostAddr: String, val portNum: Int ) : ActorBasic(name){
    //val pengine = Prolog()

    protected val actorMap : MutableMap<String, ActorBasic> =
        mutableMapOf<String, ActorBasic>()
    val proxyMap : MutableMap<String, NodeProxy> =
        mutableMapOf<String, NodeProxy>()

    companion object {
        enum class CtxMsg{
            attach, remove
        }
    }

    init{
        println("QakContext $name | INIT on port=$portNum CREATES the QakContextServer")
        QakContextServer( this, "Node$name", Protocol.TCP )
    }

    override suspend fun actorBody(msg : ApplMessage){
        println("QakContext $name |  receives $msg " )
    }

    fun addActor( actor: ActorBasic ) {
        actor.context = this
        actorMap.put( actor.name, actor )
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