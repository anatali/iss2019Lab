package bls19d.messages

import it.unibo.`is`.interfaces.protocols.IConnInteraction
import it.unibo.bls19d.messages.ApplMessage
import it.unibo.supports.FactoryProtocol

enum class Protocol {
    SERIAL, TCP, UDP, BTH
}

object UtilsMsg{
    var count = 0

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

    fun buildDispatch( content : String ) : ApplMessage {
        return ApplMessage("ledCmd", "dispatch",
            "ledProxy", "ledFrontEnd", "$content", "" + count++)
    }

}