/* Generated by AN DISI Unibo */ 
package it.unibo.control

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Control ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
					}
					 transition(edgeName="t02",targetState="sOn",cond=whenDispatch("buttonCmd"))
					transition(edgeName="t03",targetState="sOn",cond=whenEvent("local_buttonCmd"))
				}	 
				state("sOn") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						emit("ledCmd", "ledCmd(on)" ) 
						TimerActor("timer", scope, context!!, "local_tout_sOn", 200.toLong())
					}
					 transition(edgeName="t14",targetState="sOff",cond=whenTimeout("local_tout_sOn"))   
					transition(edgeName="t15",targetState="s0",cond=whenDispatch("buttonCmd"))
					transition(edgeName="t16",targetState="s0",cond=whenEvent("local_buttonCmd"))
				}	 
				state("sOff") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						emit("ledCmd", "ledCmd(off)" ) 
						TimerActor("timer", scope, context!!, "local_tout_sOff", 200.toLong())
					}
					 transition(edgeName="t27",targetState="sOn",cond=whenTimeout("local_tout_sOff"))   
					transition(edgeName="t28",targetState="s0",cond=whenDispatch("buttonCmd"))
					transition(edgeName="t29",targetState="s0",cond=whenEvent("local_buttonCmd"))
				}	 
			}
		}
}
