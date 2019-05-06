/* Generated by AN DISI Unibo */ 
package it.unibo.robotcontrol

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Robotcontrol ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
					}
					 transition(edgeName="t01",targetState="handleCmd",cond=whenDispatch("robotCmd"))
				}	 
				state("handleCmd") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("robotCmd(X)"), Term.createTerm("robotCmd(CMD)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("sendToRobot", "msg(${meta_msgArg(0)})" ,"clienttcp" ) 
						}
					}
					 transition(edgeName="t12",targetState="handleCmd",cond=whenEvent("robotCmd"))
					transition(edgeName="t13",targetState="handleCollision",cond=whenEvent("collision"))
				}	 
				state("handleCollision") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("sendToRobot", "msg(stop)" ,"clienttcp" ) 
						forward("sendToRobot", "msg(moveleft)" ,"clienttcp" ) 
					}
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
			}
		}
}