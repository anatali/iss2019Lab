package resources

import it.unibo.kactor.sysUtil
import it.unibo.kactor.*
import kotlinx.coroutines.launch 
import kotlinx.coroutines.GlobalScope

class ledManager   {
  
	companion object{
		var counter = 1;
		val ctx = sysUtil.getContext("ctxLedsPlus")
		var ledList = mutableListOf<ActorBasic>()
 		fun addNewLed() {
			val newLed = sysUtil.createActor(ctx!!, "newled${counter++}", "resources.LedActork" ) //GlobalScope
			//GlobalScope.launch{ MsgUtil.sendMsg( "ledCmd","ledCmd(on)",newLed ) }
			ledList.add( newLed )			 
		}
		fun removeLed() {
			if( ledList.size > 0) {  //at least one remains
				val firstLed = ledList.removeAt(0)
				println("removeLed ${firstLed.name} numeOfLeds= ${ledList.size}")
				firstLed.terminate()     //please complete (normally) what you were doing and terminate
				//GlobalScope.launch{ MsgUtil.sendMsg( "sysCmd","stopTheActor",firstLed ) }
			}
			 
		}	
	}
 
 
}