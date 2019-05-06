/* Generated by AN DISI Unibo */ 
package it.unibo.dynamo

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Dynamo ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						stateTimer = TimerActor("timer_s0", scope, context!!, "local_tout_s0", 3000.toLong())
					}
					 transition(edgeName="t00",targetState="sAdd",cond=whenTimeout("local_tout_s0"))   
				}	 
				state("sAdd") { //this:State
					action { //it:State
						resources.ledManager.addNewLed()
						stateTimer = TimerActor("timer_sAdd", scope, context!!, "local_tout_sAdd", 4000.toLong())
					}
					 transition(edgeName="t01",targetState="sRemove",cond=whenTimeout("local_tout_sAdd"))   
				}	 
				state("sRemove") { //this:State
					action { //it:State
						resources.ledManager.removeLed()
					}
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
			}
		}
}
