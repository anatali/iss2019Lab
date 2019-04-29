package resources

import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope

class LedActork( name : String, scope: CoroutineScope ) : ActorBasic( name, scope ){
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