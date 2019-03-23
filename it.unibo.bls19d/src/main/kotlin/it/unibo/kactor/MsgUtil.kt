package it.unibo.kactor
//FILE MsgUtil.kt

import it.unibo.`is`.interfaces.protocols.IConnInteraction
import it.unibo.supports.FactoryProtocol
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

enum class Protocol {
    SERIAL, TCP, UDP, BTH
}
object MsgUtil {
var count = 1;

    fun startMsg() : ApplMessage {
        count++
        return ApplMessage("msg( start, dispatch, main, none, start, $count )")
    }
    fun stoptMsg() : ApplMessage {
        count++
        return ApplMessage("msg( stop, dispatch, main, none, stop, $count )")
    }


    //@kotlinx.coroutines.ObsoleteCoroutinesApi
    fun forward(msg: ApplMessage, destActor: ActorBasic) {
        GlobalScope.launch {
            destActor.getChannel().send(msg)
        }
    }
    fun forward(msg: ApplMessage, destActor: String) {
        GlobalScope.launch {
            //destActor.getChannel().send(msg)
        }
    }

    fun getFactoryProtocol(protocol: Protocol) : FactoryProtocol?{
        var factoryProtocol : FactoryProtocol? = null
        when( protocol ){
            Protocol.SERIAL -> println("WARNING: TODO")
            Protocol.TCP , Protocol.UDP -> factoryProtocol =
                FactoryProtocol(null, "$protocol", "LedFrontEnd")

            else -> {
                println("WARNING: protocol unknown")
            }
        }
        return factoryProtocol
    }

    //fun getProperConnection
    fun getConnection(protocol: Protocol, hostName: String, portNum: Int, clientName:String) : IConnInteraction? {
        when( protocol ){
            Protocol.TCP , Protocol.UDP -> {
                val factoryProtocol = FactoryProtocol(null, "$protocol", clientName)
                val conn = factoryProtocol.createClientProtocolSupport(hostName, portNum)
                return conn
            }
            else -> return null
        }
    }
    fun getConnectionSerial( portName: String, rate: Int) : IConnInteraction {
        val  factoryProtocol =  FactoryProtocol(null,"${Protocol.SERIAL}",portName)
        val conn = factoryProtocol.createSerialProtocolSupport(portName)
        return conn
    }

}