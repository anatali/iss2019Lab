/* Generated by AN DISI Unibo */ 
package it.unibo.testguard

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Testguard ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
		//val Task = "preparing"
		val Task = "adding"
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("&&&  testGuard STARTED")
						forward("near", "near" ,"testguard" ) 
						forward("inRH", "inRH" ,"testguard" ) 
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("work") { //this:State
					action { //it:State
						println("&&&  testGuard work")
					}
					 transition(edgeName="t00",targetState="handlePrepare",cond=whenDispatchGuarded("near",{Task == "preparing"}))
					transition(edgeName="t01",targetState="handleAdd",cond=whenDispatchGuarded("near",{Task == "adding"}))
					transition(edgeName="t02",targetState="handleInRH",cond=whenDispatch("inRH"))
				}	 
				state("handlePrepare") { //this:State
					action { //it:State
						println("&&&  testGuard handlePrepare")
						println("$name in ${currentState.stateName} | $currentMsg")
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("handleAdd") { //this:State
					action { //it:State
						println("&&&  testGuard handleAdd")
						println("$name in ${currentState.stateName} | $currentMsg")
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("handleInRH") { //this:State
					action { //it:State
						println("&&&  testGuard handleInRH")
						println("$name in ${currentState.stateName} | $currentMsg")
					}
				}	 
			}
		}
}
