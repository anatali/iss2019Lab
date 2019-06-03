/* Generated by AN DISI Unibo */ 
package it.unibo.frontend

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Frontend ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("frontend STARTS")
						resources.buttonEventEmitter.create(myself ,"" )
						stateTimer = TimerActor("timer_s0", 
							scope, context!!, "local_tout_frontend_s0", 600000.toLong() )
					}
					 transition(edgeName="t00",targetState="s0",cond=whenTimeout("local_tout_frontend_s0"))   
				}	 
			}
		}
}
