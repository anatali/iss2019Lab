package it.unibo.kactor

import it.unibo.bls19d.chain.led.LedProxy

open class NodeContext(val name: String, val hostAddr: String, val portNum: Int ){

    protected val actorMap : MutableMap<String, ActorBasic> =
                                            mutableMapOf<String, ActorBasic>()
    val proxyMap : MutableMap<String, NodeProxy> =
                                            mutableMapOf<String, NodeProxy>()


    init{
        println("NodeContext $name INIT on port=$portNum")
        NodeServer( this, "Node$name", Protocol.TCP )
    }
    fun addActor( actor: ActorBasic ) {
        actorMap.put( actor.name, actor )
    }

    fun hasActor( actorName: String ) : Boolean {
        return actorMap.containsKey(actorName)
    }


    fun addCtxProxy( ctx : NodeContext ){
        val proxy = NodeProxy("proxy${ctx.name}", Protocol.TCP, ctx.hostAddr, ctx.portNum)
        proxyMap.put( ctx.name, proxy )
    }
}