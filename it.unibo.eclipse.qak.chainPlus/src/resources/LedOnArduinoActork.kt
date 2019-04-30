package resources

import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope

class LedOnArduinoActork( name : String, scope: CoroutineScope=GlobalScope ) : ActorBasic( name, scope ){
    val concreteLed = ledProxyArduino("COM9",9600)

    override suspend fun actorBody(msg : ApplMessage){
        //println("   LedOnArduinoActork $name |  msg= $msg "  )
        when( msg.msgContent() ){
            "ledCmd(on)"  -> concreteLed.turnOn()
            "ledCmd(off)" -> concreteLed.turnOff()
            else -> println("   LedOnArduinoActork $name | UNKNOWN $msg")
        }
    }
}