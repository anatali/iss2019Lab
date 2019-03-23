package it.unibo.bls19d.chain

import it.unibo.kactor.ApplMessage
import it.unibo.kactor.Protocol

/*
WARNING: avoid upcase letters
 */
enum class LedMsg(val cmd: String) {
    startBlink("startBlink"), stopBlink("stopBlink"),goonBlink("goonBlink"),
    on("on"), off("off")
}

enum class ChainMsg{
    startChainBlink, stopChainBlink
}

sealed class LedCmd( val cmd: LedMsg ){
    override fun toString() : String{
        return "ledCmd( ${cmd.name} )"
    }
    class LedOn  : LedCmd(LedMsg.on)
    class LedOff : LedCmd(LedMsg.off)
}

var count = 0
//msg( MSGID: String, MSGTYPE: String, SENDER: String, RECEIVER: String, CONTENT: String, SEQNUM: String )
class ApplLedCmd( cmd: LedMsg, sender: String, receiver:String ) : ApplMessage(
    "ledCmd", "dispatch", sender, receiver, cmd.toString(), "${count++}")


class ChainRegister(val host: String, val port: String,
                    val protocol : Protocol = Protocol.TCP, val msgId : String ="chainRegister" ){
    override fun toString() : String{
        return "register($host,$port,$protocol)"
    }
}