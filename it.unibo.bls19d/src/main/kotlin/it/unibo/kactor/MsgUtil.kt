package it.unibo.kactor
//FILE MsgUtil.kt

import alice.tuprolog.Struct
import alice.tuprolog.Term
import it.unibo.`is`.interfaces.protocols.IConnInteraction
import it.unibo.supports.FactoryProtocol
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

enum class Protocol {
    SERIAL, TCP, UDP, BTH
}

enum class ApplMessageType{
    dispatch, request, invitation
}

open class ApplMessage  {
    //msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM )
    protected var msgId: String = ""
    protected var msgType: String? = null
    protected var msgSender: String = ""
    protected var msgReceiver: String = ""
    protected var msgContent: String = ""
    protected var msgNum: Int = 0

    val term: Term
        get() = if (msgType == null)
            Term.createTerm("msg(none,none,none,none,none,0)")
        else
            Term.createTerm(msgContent)

    //@Throws(Exception::class)
    constructor(
        MSGID: String, MSGTYPE: String, SENDER: String, RECEIVER: String, CONTENT: String, SEQNUM: String) {
        msgId = MSGID
        msgType = MSGTYPE
        msgSender = SENDER
        msgReceiver = RECEIVER
        msgContent = envelope(CONTENT)
        msgNum = Integer.parseInt(SEQNUM)
        //		System.out.println("ApplMessage " + MSGID + " " + getDefaultRep() );
    }

    //@Throws(Exception::class)
    constructor(msg: String) {
        val msgStruct = Term.createTerm(msg) as Struct
        setFields(msgStruct)
    }

    private fun setFields(msgStruct: Struct) {
        msgId = msgStruct.getArg(0).toString()
        msgType = msgStruct.getArg(1).toString()
        msgSender = msgStruct.getArg(2).toString()
        msgReceiver = msgStruct.getArg(3).toString()
        msgContent = msgStruct.getArg(4).toString()
        msgNum = Integer.parseInt(msgStruct.getArg(5).toString())
    }

    fun msgId(): String {
        return msgId
    }

    fun msgType(): String? {
        return msgType
    }

    fun msgSender(): String {
        return msgSender
    }

    fun msgReceiver(): String {
        return msgReceiver
    }

    fun msgContent(): String {
        return msgContent
    }

    fun msgNum(): String {
        return "" + msgNum
    }

    override fun toString(): String {
        return getDefaultRep()
    }

    fun getDefaultRep(): String {
        return if (msgType == null)
            "msg(none,none,none,none,none,0)"
        else
            "msg($msgId,$msgType,$msgSender,$msgReceiver,$msgContent,${msgNum()})"
    }

    protected fun envelope(content: String): String {
        try {
            val tt = Term.createTerm(content)
            return tt.toString()
        } catch (e: Exception) {
            return "'$content'"
        }
    }

}//ApplMessage

object MsgUtil {
var count = 1;

    fun buildDispatch( actor: String, msgId : String , content : String, dest: String ) : ApplMessage {
        return ApplMessage(msgId, ApplMessageType.dispatch.toString(),
            actor, dest, "$content", "${count++}")
    }

    suspend fun sendMsg( sender : String, msgId: String, msg: String, destActor: ActorBasic) {
        val dispatchMsg = buildDispatch(sender, msgId, msg, destActor.name)
        //println("sendMsg $dispatchMsg")
        destActor.actor.send( dispatchMsg )
    }
    suspend fun sendMsg(msg: ApplMessage, destActor: ActorBasic) {
        destActor.actor.send(msg)
    }
    suspend fun sendMsg(msgId: String, msg: String, destActor: ActorBasic) {
        val dispatchMsg = buildDispatch("any", msgId, msg, destActor.name)
        //println("sendMsg $dispatchMsg")
        destActor.actor.send(dispatchMsg)
    }

    fun getFactoryProtocol(protocol: Protocol) : FactoryProtocol?{
        var factoryProtocol : FactoryProtocol? = null
        when( protocol ){
            Protocol.SERIAL -> println("WARNING: TODO")
            Protocol.TCP , Protocol.UDP -> factoryProtocol =
                FactoryProtocol(null, "$protocol", "LedFrontEnd")
            else -> println("WARNING: protocol unknown")
        }
        return factoryProtocol
    }

    fun getConnection(protocol: Protocol, hostName: String, portNum: Int, clientName:String) : IConnInteraction? {
        when( protocol ){
            Protocol.TCP , Protocol.UDP -> {
                val factoryProtocol = FactoryProtocol(null, "$protocol", clientName)
                val conn = factoryProtocol.createClientProtocolSupport(hostName, portNum)
                return conn
            }
            else -> {
                 return null
            }
        }
    }
    fun getConnectionSerial( portName: String, rate: Int) : IConnInteraction {
        val  factoryProtocol =  FactoryProtocol(null,"${Protocol.SERIAL}",portName)
        val conn = factoryProtocol.createSerialProtocolSupport(portName)
        return conn
    }

}