/* Generated by AN DISI Unibo */ 
package it.unibo.sonarhandler

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Sonarhandler ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "init"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						println("sonardatahandler STARTS ")
					}
					 transition( edgeName="goto",targetState="waitForEvents", cond=doswitch() )
				}	 
				state("waitForEvents") { //this:State
					action { //it:State
						stateTimer = TimerActor("timer_waitForEvents", 
							scope, context!!, "local_tout_sonarhandler_waitForEvents", 60000.toLong() )
					}
					 transition(edgeName="t13",targetState="endOfJob",cond=whenTimeout("local_tout_sonarhandler_waitForEvents"))   
					transition(edgeName="t14",targetState="sendToRadar",cond=whenEvent("sonar"))
					transition(edgeName="t15",targetState="showObstacle",cond=whenEvent("sonarDetect"))
				}	 
				state("sendToRadar") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("sonar(SONAR,TARGET,DISTANCE)"), Term.createTerm("sonar(SONAR,TARGET,DISTANCE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 val D = Integer.parseInt( payloadArg(2) ) * 5
								emit("polar", "p($D,90)" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitForEvents", cond=doswitch() )
				}	 
				state("showObstacle") { //this:State
					action { //it:State
						println("sonardetector showObstacle")
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
