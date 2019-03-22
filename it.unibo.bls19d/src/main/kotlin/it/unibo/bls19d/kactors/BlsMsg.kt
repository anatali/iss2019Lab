package it.unibo.bls19d.kactors

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

class ChainRegister(val host: String, val port: String,
                    val protocol : Protocol = Protocol.TCP, val msgId : String ="chainRegister" ){
    override fun toString() : String{
        return "register($host,$port,$protocol)"
    }
}