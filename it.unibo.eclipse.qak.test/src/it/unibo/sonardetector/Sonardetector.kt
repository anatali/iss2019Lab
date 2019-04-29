/* Generated by AN DISI Unibo */ 
package it.unibo.sonardetector

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Sonardetector ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "init"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						println("sonardetector STARTS ")
					}
					 transition( edgeName="goto",targetState="waitForEvents", cond=doswitch() )
				}	 
				state("waitForEvents") { //this:State
					action { //it:State
						TimerActor("timer", scope, context!!, "local_tout_waitForEvents", 60000.toLong())
					}
					 transition(edgeName="t117",targetState="endOfJob",cond=whenTimeout("local_tout_waitForEvents"))   
					transition(edgeName="t118",targetState="sendToRadar",cond=whenEvent("sonar"))
					transition(edgeName="t119",targetState="showObstacle",cond=whenEvent("sonarDetect"))
				}	 
				state("sendToRadar") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("sonar(SONAR,TARGET,DISTANCE)"), Term.createTerm("sonar(SONAR,TARGET,DISTANCE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								emit("polar", "p(${meta_msgArg(2)},90)" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitForEvents", cond=doswitch() )
				}	 
				state("showObstacle") { //this:State
					action { //it:State
						println("showObstacle")
					}
					 transition( edgeName="goto",targetState="waitForEvents", cond=doswitch() )
				}	 
				state("endOfJob") { //this:State
					action { //it:State
					}
				}	 
			}
		}
}
