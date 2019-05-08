package resources

import it.unibo.kactor.sysUtil
import it.unibo.kactor.*
import kotlinx.coroutines.launch 
import kotlinx.coroutines.GlobalScope
import it.unibo.blsFramework.kotlin.fsm.forward

class ledManager   {
  
	companion object{
		var counter = 1;
		//val ctx         = sysUtil.getContext("ctxLedsPlus")
		var ledList     = mutableListOf<ActorBasic>()
		var ledNameList = mutableListOf<String>()
 		
		fun addNewLed( actor: ActorBasicFsm ) : String {
			val ctx = actor.context
			val newLed = sysUtil.createActor(ctx!!, "newled${counter++}", "resources.LedActork" ) //GlobalScope
			ledList.add( newLed!! )
			ledNameList.add( newLed!!.name )
			//actor.solve( "addLed( ${newLed.name} )")
			println("ledManager addNewLed ${newLed!!.name} numeOfLeds= ${ledList.size}")
			return 	newLed.name	 
		}
		
  
		fun removeLastLed( actor: ActorBasicFsm ) : ActorBasic? {
			println("ledManager removeLastLed numOfLeds= ${ledList.size}")
			if( ledList.size > 0) {  //at least one remains
				val lastLed  = ledList.last()
				actor.scope.launch{ actor.forward( "ledCmd", "ledCmd(out)",  lastLed  )   }
 				ledNameList.remove( lastLed.name )
				ledList.remove( lastLed  )
				println("ledManager removeLed ${lastLed.name} numOfLeds= ${ledList.size}")
				actor.solve( "removeLed(${lastLed.name})")
				//lastLed.terminate()     //please complete (normally) what you were doing and terminate
				return lastLed 
 			}else return null			 
		}
		
		fun getExtraChainNames() : List<String>{
			return ledNameList
		}
			
	}
 
 
}