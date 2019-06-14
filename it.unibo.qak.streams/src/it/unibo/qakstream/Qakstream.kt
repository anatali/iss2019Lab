/* Generated by AN DISI Unibo */ 
package it.unibo.qakstream

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Qakstream ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("qakstream STARTS")
						itunibo.streams.sonarStreamPipe.create(myself)
						println("qakstream DONE")
					}
					 transition( edgeName="goto",targetState="doWork", cond=doswitch() )
				}	 
				state("doWork") { //this:State
					action { //it:State
					}
					 transition(edgeName="t00",targetState="handleObstacle",cond=whenEvent("obstacle"))
				}	 
				state("handleObstacle") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
					}
					 transition( edgeName="goto",targetState="doWork", cond=doswitch() )
				}	 
			}
		}
}
