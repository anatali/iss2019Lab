package resources

sealed class BlsMsgs( val cmd: String, val id : String ="blsCmd"  ){
    override fun toString() : String{ return "$id(${cmd})" }

    class LedCmd( cmd : String ) : BlsMsgs(cmd, id=LedCmd.id){
        companion object{ val id = "ledCmd" }
    }
    class ButtonCmd( cmd : String ) : BlsMsgs( cmd, id=ButtonCmd.id){
        companion object{ val id = "local_buttonCmd" }
    }
    class ControlCmd(cmd : String) : BlsMsgs( cmd, id=ControlCmd.id){
        companion object{ val id = "controlCmd" }
    }
}