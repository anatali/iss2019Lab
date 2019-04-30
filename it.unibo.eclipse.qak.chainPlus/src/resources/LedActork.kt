package resources

import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope

class LedActork( name : String, scope: CoroutineScope=GlobalScope ) : ActorBasic( name, scope ){
    val concreteLed = myLedSegm()

    override suspend fun actorBody(msg : ApplMessage){
        //println("   LedActork $name |  msg= $msg "  )
        when( msg.msgContent() ){
            "ledCmd(on)"  -> concreteLed.turnOn()
            "ledCmd(off)" -> concreteLed.turnOff()
            else -> println("   LedActork $name | UNKNOWN $msg")
        }
    }
}