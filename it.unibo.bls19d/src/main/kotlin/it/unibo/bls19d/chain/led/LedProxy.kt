package it.unibo.bls19d.chain.led

import it.unibo.`is`.interfaces.protocols.IConnInteraction
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.Protocol

class LedProxy(name:String, val protocol: Protocol,
               val hostName: String,  val portNum: Int ) :  ActorBasic( name ){
    protected var conn: IConnInteraction? = null

    init {
        configure()
    }
    fun configure() {
        when (protocol) {
            Protocol.SERIAL -> conn = MsgUtil.getConnectionSerial("", 9600)
            Protocol.TCP, Protocol.UDP ->
                conn = MsgUtil.getConnection(protocol, hostName, portNum, "ledProxy")
            else -> println("WARNING: protocol unknown")
        }
    }

    //Routes each message to the connected server
    override suspend fun actorBody(msg : ApplMessage){
        //println("               LedProxy $name receives $msg  conn=$conn")
        conn?.sendALine("$msg")
    }



}