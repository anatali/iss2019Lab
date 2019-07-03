/* Generated by AN DISI Unibo */ 
package it.unibo.onestepahead

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Onestepahead ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		 
		var foundObstacle = false; 
		var StepTime = 0L; 
		var Duration=0 
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						foundObstacle = false 
					}
					 transition(edgeName="t06",targetState="doMoveForward",cond=whenDispatch("onestep"))
				}	 
				state("doMoveForward") { //this:State
					action { //it:State
						storeCurrentMessageForReply()
						if( checkMsgContent( Term.createTerm("onestep(DURATION)"), Term.createTerm("onestep(TIME)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								StepTime = payloadArg(0).toLong()
								forward("modelChange", "modelChange(robot,w)" ,"resourcemodel" ) 
								startTimer()
						}
						stateTimer = TimerActor("timer_doMoveForward", 
							scope, context!!, "local_tout_onestepahead_doMoveForward", StepTime )
					}
					 transition(edgeName="t07",targetState="endDoMoveForward",cond=whenTimeout("local_tout_onestepahead_doMoveForward"))   
					transition(edgeName="t08",targetState="stepFail",cond=whenEvent("obstacle"))
					transition(edgeName="t09",targetState="s0",cond=whenDispatch("stopAppl"))
					transition(edgeName="t010",targetState="stepFail",cond=whenEvent("sonarRobot"))
				}	 
				state("endDoMoveForward") { //this:State
					action { //it:State
						forward("modelChange", "modelChange(robot,h)" ,"resourcemodel" ) 
						replyToCaller("stepOk", "stepOk(ok)")
					}
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
				state("handleSonarRobot") { //this:State
					action { //it:State
						println("onestepahead handleSonarRobot  ")
						Duration=getDuration()
						if( checkMsgContent( Term.createTerm("sonar(DISTANCE)"), Term.createTerm("sonar(DISTANCE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								val distance = Integer.parseInt( payloadArg(0) ) 
								              foundObstacle = (distance<20) 
						}
					}
					 transition( edgeName="goto",targetState="stepFail", cond=doswitchGuarded({foundObstacle}) )
					transition( edgeName="goto",targetState="s0", cond=doswitchGuarded({! foundObstacle}) )
				}	 
				state("stepFail") { //this:State
					action { //it:State
						Duration=getDuration()
						println("onestepahead stepFail Duration=$Duration ")
						replyToCaller("stepFail", "stepFail(obstacle,$Duration)")
					}
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
			}
		}
}
