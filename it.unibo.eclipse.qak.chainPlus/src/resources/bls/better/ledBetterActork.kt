package resources.bls.better

import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import devices.gui.LedAsGui
import resources.myLedSegm

class ledBetterActork( name : String, scope: CoroutineScope=GlobalScope ) : ActorBasic( name, scope ){
    val concreteLed = myLedSegm()

	companion object{
		fun create(){
			ledBetterActork("led")  	//singleton
		}
 		
	}
    override suspend fun actorBody(msg : ApplMessage){
        //println("   LedActork $name |  msg= $msg "  )
        when( msg.msgContent() ){
            "ledCmd(on)"  -> concreteLed.turnOn()
            "ledCmd(off)" -> concreteLed.turnOff()
			"ledCmd(out)" -> concreteLed.hideLed()
            //else -> println("   LedActork $name | UNKNOWN $msg")
        }
    }
}