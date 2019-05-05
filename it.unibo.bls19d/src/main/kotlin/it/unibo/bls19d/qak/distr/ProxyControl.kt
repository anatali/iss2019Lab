package it.unibo.bls19d.qak.distr

import it.unibo.`is`.interfaces.protocols.IConnInteraction
import it.unibo.bls19d.qak.SystemKb
import it.unibo.kactor.*
import it.unibo.kactor.Protocol

class ProxyControl( name : String, val protocol: Protocol,
                    val hostName: String,  val portNum: Int ) : ActorBasic( name ){

    protected var conn: IConnInteraction? = null
    init { configure()  }

    fun configure() {
        SystemKb.blsActorMap.put(  name, this )
        when (protocol) {
            Protocol.TCP, Protocol.UDP ->
                conn = MsgUtil.getConnection(protocol, hostName, portNum, name)
            Protocol.SERIAL -> conn = MsgUtil.getConnectionSerial("", 9600)
            else -> println("WARNING: protocol unknown")
        }
    }

    override suspend fun actorBody(msg : ApplMessage){
        println("   ProxyControl $name |  msg= $msg "  )
        conn?.sendALine("$msg")
    }

}