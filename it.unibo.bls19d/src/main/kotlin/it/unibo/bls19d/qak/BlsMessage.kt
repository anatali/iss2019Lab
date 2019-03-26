package it.unibo.bls19d.qak

sealed class BlsCmd( val cmd: String , val id : String ="blsMsg" ){
    override fun toString() : String{
        return "$id(${cmd})"
    }

    class LedOn  : BlsCmd("on")
    class LedOff : BlsCmd("off")

    class ButtonCmd : BlsCmd( "click")

    class BlinkStartCmd: BlsCmd( "startBlink")
    class BlinkStopCmd: BlsCmd( "stopBlink")
}