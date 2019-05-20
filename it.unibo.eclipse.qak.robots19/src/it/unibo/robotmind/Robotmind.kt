/* Generated by AN DISI Unibo */ 
package it.unibo.robotmind

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Robotmind ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		var obstacle = false
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("ROBOT MIND STARTED")
						forward("robotCmd", "robotCmd(a)" ,"basicrobot" ) 
						delay(700) 
						forward("robotCmd", "robotCmd(d)" ,"basicrobot" ) 
						delay(700) 
						forward("robotCmd", "robotCmd(h)" ,"basicrobot" ) 
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
					}
					 transition(edgeName="t01",targetState="handleCmd",cond=whenDispatch("robotCmd"))
					transition(edgeName="t02",targetState="handleCmd",cond=whenEvent("envCond"))
					transition(edgeName="t03",targetState="handleSonarRobot",cond=whenEvent("sonarRobot"))
					transition(edgeName="t04",targetState="handleModelChanged",cond=whenEvent("modelChanged"))
				}	 
				state("handleCmd") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("robotCmd(CMD)"), Term.createTerm("robotCmd(CMD)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("modelChange", "modelChange(robot,${payloadArg(0)})" ,"resourcemodel" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("handleModelChanged") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("modelChanged(TARGET,VALUE)"), Term.createTerm("modelChanged(robot,CMD)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("robotCmd", "robotCmd(${payloadArg(1)})" ,"basicrobot" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("handleSonarRobot") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("sonar(DISTANCE)"), Term.createTerm("sonar(DISTANCE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								obstacle = Integer.parseInt( payloadArg(0) ) < 10 
						}
					}
					 transition( edgeName="goto",targetState="handeObstacle", cond=doswitchGuarded({obstacle}) )
					transition( edgeName="goto",targetState="waitCmd", cond=doswitchGuarded({! obstacle}) )
				}	 
				state("handeObstacle") { //this:State
					action { //it:State
						println("handeObstacle: going back START")
						forward("modelChange", "modelChange(robot,back)" ,"resourcemodel" ) 
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
