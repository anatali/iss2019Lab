/* Generated by AN DISI Unibo */ 
package it.unibo.resourcemodel

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Resourcemodel ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						solve("consult('sysRules.pl')","") //set resVar	
						solve("consult('ddrWorkerResourceModel.pl')","") //set resVar	
						solve("showResourceModel","") //set resVar	
					}
					 transition( edgeName="goto",targetState="waitModelChange", cond=doswitch() )
				}	 
				state("waitModelChange") { //this:State
					action { //it:State
					}
					 transition(edgeName="t01",targetState="changeModel",cond=whenEvent("modelChange"))
				}	 
				state("changeModel") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("modelChange(TARGET,VALUE)"), Term.createTerm("modelChange(robot,V)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								solve("action(robot,move(${payloadArg(1)}))","") //set resVar	
								if(currentSolution.isSuccess()) emit("modelChanged", "modelChanged(robot,${payloadArg(1)})" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitModelChange", cond=doswitch() )
				}	 
			}
		}
}
