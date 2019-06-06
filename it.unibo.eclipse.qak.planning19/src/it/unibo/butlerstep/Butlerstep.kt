/* Generated by AN DISI Unibo */ 
package it.unibo.butlerstep

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Butlerstep ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		 var foundObstacle = false; var StepTime = 0L
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						foundObstacle = false 
					}
					 transition(edgeName="t02",targetState="doMoveForward",cond=whenDispatch("onestep"))
				}	 
				state("doMoveForward") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("onestep(DURATION)"), Term.createTerm("onestep(TIME)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								StepTime = payloadArg(0).toLong(); println(StepTime)
								forward("modelChange", "modelChange(robot,w)" ,"resourcemodel" ) 
								itunibo.planner.plannerUtil.startTimer(  )
						}
						stateTimer = TimerActor("timer_doMoveForward", 
							scope, context!!, "local_tout_butlerstep_doMoveForward", StepTime )
					}
					 transition(edgeName="t03",targetState="endDoMoveForward",cond=whenTimeout("local_tout_butlerstep_doMoveForward"))   
					transition(edgeName="t04",targetState="stepFail",cond=whenEvent("obstacle"))
				}	 
				state("endDoMoveForward") { //this:State
					action { //it:State
						forward("modelChange", "modelChange(robot,h)" ,"resourcemodel" ) 
						forward("stepOk", "stepOk(ok)" ,"butler" ) 
					}
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
				state("stepFail") { //this:State
					action { //it:State
						itunibo.planner.moveUtils.setDuration(myself)
						println("&&& butlerstep stepfail ")
						solve("wduration(TIME)","") //set resVar	
						forward("stepFail", "stepFail(obstacle,${getCurSol("TIME").toString()})" ,"butler" ) 
					}
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
			}
		}
}
