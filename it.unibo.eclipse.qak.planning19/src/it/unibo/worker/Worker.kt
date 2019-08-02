/* Generated by AN DISI Unibo */ 
package it.unibo.worker

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Worker ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
		var mapEmpty    = false
		val mapname     = "roomMbot" //yyy  
		var Curmove     = "" 
		var curmoveIsForward = false 
		
		//REAL ROBOT
		var StepTime   = 1000 	 
		var PauseTime  = 500 
		
		//VIRTUAL ROBOT
		//var StepTime   = 330	//for virtual
		//var PauseTime  = 500
		
		var PauseTimeL  = PauseTime.toLong()
		
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						solve("consult('moves.pl')","") //set resVar	
						itunibo.coap.client.resourceObserverCoapClient.create( "coap://localhost:5683/resourcemodel"  )
						itunibo.planner.plannerUtil.initAI(  )
						itunibo.planner.moveUtils.loadRoomMap(myself ,mapname )
						itunibo.planner.moveUtils.showCurrentRobotState(  )
							val MapStr =  itunibo.planner.plannerUtil.getMapOneLine()  
						forward("modelUpdate", "modelUpdate(roomMap,$MapStr)" ,"resourcemodel" ) 
						println("&&&  workerinroom STARTED")
					}
					 transition(edgeName="t02",targetState="setGoalAndDo",cond=whenDispatch("moveButlerTo"))
				}	 
				state("setGoalAndDo") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("moveButlerTo(X,Y,D)"), Term.createTerm("moveButlerTo(X,Y,D)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("$name in ${currentState.stateName} | $currentMsg")
								storeCurrentMessageForReply()
								itunibo.planner.plannerUtil.setGoal( payloadArg(0), payloadArg(1)  )
								itunibo.planner.moveUtils.doPlan(myself)
						}
					}
					 transition( edgeName="goto",targetState="executePlannedActions", cond=doswitch() )
				}	 
				state("executePlannedActions") { //this:State
					action { //it:State
						solve("retract(move(M))","") //set resVar	
						if(currentSolution.isSuccess()) { Curmove = getCurSol("M").toString() 
						              curmoveIsForward=(Curmove == "w")
						 }
						else
						{ Curmove = ""; curmoveIsForward=false
						 }
					}
					 transition( edgeName="goto",targetState="checkAndDoAction", cond=doswitchGuarded({(Curmove.length>0) }) )
					transition( edgeName="goto",targetState="goalOk", cond=doswitchGuarded({! (Curmove.length>0) }) )
				}	 
				state("goalOk") { //this:State
					action { //it:State
							val MapStr =  itunibo.planner.plannerUtil.getMapOneLine()  
						forward("modelUpdate", "modelUpdate(roomMap,$MapStr)" ,"resourcemodel" ) 
						replyToCaller("goalReached", "goalReached(ok)")
					}
					 transition(edgeName="t03",targetState="setGoalAndDo",cond=whenDispatch("moveButlerTo"))
				}	 
				state("checkAndDoAction") { //this:State
					action { //it:State
					}
					 transition( edgeName="goto",targetState="doForwardMove", cond=doswitchGuarded({curmoveIsForward}) )
					transition( edgeName="goto",targetState="doTheMove", cond=doswitchGuarded({! curmoveIsForward}) )
				}	 
				state("doTheMove") { //this:State
					action { //it:State
						itunibo.planner.moveUtils.rotate(myself ,Curmove )
						solve("dialog(F)","") //set resVar	
					}
					 transition( edgeName="goto",targetState="executePlannedActions", cond=doswitch() )
				}	 
				state("doForwardMove") { //this:State
					action { //it:State
						delay(PauseTimeL)
						itunibo.planner.moveUtils.attemptTomoveAhead(myself ,StepTime )
					}
					 transition(edgeName="t04",targetState="handleStepOk",cond=whenDispatch("stepOk"))
					transition(edgeName="t05",targetState="hadleStepFail",cond=whenDispatch("stepFail"))
					transition(edgeName="t06",targetState="hadleStepFail",cond=whenDispatch("stepFail"))
				}	 
				state("handleStepOk") { //this:State
					action { //it:State
						itunibo.planner.moveUtils.updateMapAfterAheadOk(myself)
					}
					 transition( edgeName="goto",targetState="executePlannedActions", cond=doswitch() )
				}	 
				state("hadleStepFail") { //this:State
					action { //it:State
						println("NEVER HERE!!!")
						val ButlerDirection = itunibo.planner.moveUtils.getDirection(myself)
						println("ButlerDirection = $ButlerDirection")
						forward("robotCmd", "robotCmd(s)" ,"basicrobot" ) 
						delay(50) 
						forward("robotCmd", "robotCmd(h)" ,"basicrobot" ) 
					}
					 transition( edgeName="goto",targetState="executePlannedActions", cond=doswitch() )
				}	 
			}
		}
}
