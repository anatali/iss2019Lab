/* Generated by AN DISI Unibo */ 
package it.unibo.c

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class C ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						solve("consult('sysRules.pl')","") //set resVar	
						solve("consult('neuronConnKb.pl')","") //set resVar	
					}
					 transition( edgeName="goto",targetState="active", cond=doswitch() )
				}	 
				state("active") { //this:State
					action { //it:State
						println("neuron c is active")
					}
					 transition(edgeName="t00",targetState="elabInputOn",cond=whenDispatch("son"))
					transition(edgeName="t01",targetState="elabInputOff",cond=whenDispatch("soff"))
				}	 
				state("elabInputOn") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("son(INPUT)"), Term.createTerm("son(INPUT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								solve("update(${payloadArg(0)},on)","") //set resVar	
						}
					}
					 transition( edgeName="goto",targetState="elabOutput", cond=doswitch() )
				}	 
				state("elabInputOff") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("soff(INPUT)"), Term.createTerm("soff(INPUT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								solve("update(${payloadArg(0)},off)","") //set resVar	
						}
					}
					 transition( edgeName="goto",targetState="elabOutput", cond=doswitch() )
				}	 
				state("elabOutput") { //this:State
					action { //it:State
						solve("firing","") //set resVar	
						if(currentSolution.isSuccess()) { println("c FIRING")
						 }
						else
						{ println("c NOT FIRING")
						 }
					}
					 transition( edgeName="goto",targetState="active", cond=doswitch() )
				}	 
			}
		}
}
