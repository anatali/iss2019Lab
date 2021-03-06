/* Generated by AN DISI Unibo */ 
package it.unibo.button

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Button ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						resources.buttonEventEmitter.create(myself ,"" )
					}
					 transition( edgeName="goto",targetState="waitForUserCommand", cond=doswitch() )
				}	 
				state("waitForUserCommand") { //this:State
					action { //it:State
					}
					 transition(edgeName="t00",targetState="handleCmd",cond=whenEvent("local_buttonCmd"))
				}	 
				state("handleCmd") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("local_buttonCmd(X)"), Term.createTerm("local_buttonCmd(CMD)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("robotCmd", "robotCmd(${payloadArg(0)})" ,"robotcontrol" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitForUserCommand", cond=doswitch() )
				}	 
			}
		}
}
