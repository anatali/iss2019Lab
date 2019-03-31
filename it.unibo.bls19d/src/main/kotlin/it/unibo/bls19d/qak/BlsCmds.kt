package it.unibo.bls19d.qak

sealed class BlsCmds( val cmd: String, val id : String ="blsCmd"  ){
    override fun toString() : String{ return "$id(${cmd})" }

    class LedCmd( cmd : String ) : BlsCmds(cmd, id=LedCmd.id){
        companion object{ val id = "ledCmd" }
    }
    class ButtonCmd : BlsCmds( "click", id=ButtonCmd.id){
        companion object{ val id = "click" }
    }
    class ControlCmd(cmd : String):BlsCmds( cmd, id=ControlCmd.id){
        companion object{ val id = "controlCmd" }
    }
}