package it.unibo.kactor

import it.unibo.`is`.interfaces.protocols.IConnInteraction

class NodeProxy( name: String, val protocol: Protocol,
                 val hostName: String,  val portNum: Int ) : ActorBasic( name ){

    protected var conn: IConnInteraction? = null

    init { configure()  }

    fun configure() {
        while( conn == null  ) {
            when (protocol) {
                Protocol.TCP, Protocol.UDP ->
                    conn = MsgUtil.getConnection(protocol, hostName, portNum, "ledProxy")
                Protocol.SERIAL -> conn = MsgUtil.getConnectionSerial("", 9600)
                else -> println("WARNING: protocol unknown")
            }
            if (conn == null) {
                println("WAIT/RETRY TO SET PROXY TO $hostName:$portNum ")
                Thread.sleep(500)
            } else {
                println("PROXY DONE TO $hostName:$portNum ")
            }
        }
    }
    //Routes each message to the connected server
    override suspend fun actorBody(msg : ApplMessage){
        //println("       NodeProxy $name receives $msg conn=$conn ") // conn=$conn"
        try{
            conn?.sendALine("$msg")
        }catch( e : Exception){
            println("PROXY $name sendALine error $e ")
        }
    }

}