package it.unibo.bls19d.qak.distr

import it.unibo.`is`.interfaces.protocols.IConnInteraction
import it.unibo.bls19d.qak.SystemKb
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.Protocol
import it.unibo.supports.FactoryProtocol
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ServerControl(name : String,
           val protocol: Protocol, val portNum: Int,
                    val destName : String ) : ActorBasic( name ){
    var control : ActorBasic? = null

    protected var hostName: String? = null
    protected var factoryProtocol: FactoryProtocol? = null

    init {
        System.setProperty("inputTimeOut", "600000")  //10 minuti
        factoryProtocol = MsgUtil.getFactoryProtocol(protocol)
        control  = SystemKb.blsActorMap.get(destName) //From name to actor
        //waitForConnection()
        GlobalScope.launch(Dispatchers.IO) {
            autoMsg( "start", "start" )
            //MsgUtil.sendMsg("start", "start", this.actor )
        }
    }

    override suspend fun actorBody(msg : ApplMessage){
        println("       ServerControl $name receives $msg  ")
        waitForConnection()
    }

    suspend protected fun waitForConnection() {
        //We could handle several connections
        //GlobalScope.launch(Dispatchers.IO) {
        try {
            while (true) {
                println("   LedServer $name | WAIT FOR CONNECTION")
                val conn = factoryProtocol!!.createServerProtocolSupport(portNum) //BLOCKS
                handleConnection(conn)
            }
        } catch (e: Exception) {
            println("   LedServer $name | WARNING: ${e.message}")
        }
        //}
    }

    suspend  protected fun handleConnection(conn: IConnInteraction) {
        //GlobalScope.launch(Dispatchers.IO) {
        try {
            println("   ServerControl | handling new connection:$conn")
            while (true) {
                val msg = conn.receiveALine()       //BLOCKING
                println("   ServerControl | receives:$msg")
                val inputmsg = ApplMessage(msg)
                MsgUtil.sendMsg(inputmsg, control!!)
            }
        } catch (e: Exception) {
            println("   ServerControl $name | handleConnection WARNING: ${e.message}")
        }
        //}
    }




}