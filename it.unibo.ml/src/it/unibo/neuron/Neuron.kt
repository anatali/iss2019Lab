/* Generated by AN DISI Unibo */ 
package it.unibo.neuron

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Neuron ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						solve("consult('sysRules.pl')","") //set resVar	
						solve("consult('neuronkb.pl')","") //set resVar	
						println("neuron is idle")
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("work") { //this:State
					action { //it:State
					}
					 transition(edgeName="t00",targetState="handleStimulus",cond=whenDispatch("s"))
				}	 
				state("handleStimulus") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("s(INPUT,STATE)"), Term.createTerm("s(INPUT,V)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												val input = payloadArg(0) 
												val v     = payloadArg(1)
								println("neuron handleStimulus $input $v")
								solve("update('$input','$v',RESULT)","") //set resVar	
								 val result = currentSolution.getVarValue("RESULT").toString() 
								if(result.equals("firing")){ println("FIRING")
								 }
						}
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
			}
		}
}