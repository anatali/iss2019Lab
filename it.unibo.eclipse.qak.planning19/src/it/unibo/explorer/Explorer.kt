/* Generated by AN DISI Unibo */ 
package it.unibo.explorer

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Explorer ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
		var stepCounter = 0
		var Curmove = ""
		var curmoveIsForward = false
		var StepTime   = 700L	//long		//330L	//for virtual
		var RotateTime = 610L	//long		//300L	//for virtual
		var PauseTime  = 500L 
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("&&&  explorer STARTED")
						solve("consult('moves.pl')","") //set resVar	
						itunibo.planner.plannerUtil.initAI(  )
						println("INITIAL MAP")
						itunibo.planner.plannerUtil.showMap(  )
					}
					 transition( edgeName="goto",targetState="doExploreStep", cond=doswitch() )
				}	 
				state("doExploreStep") { //this:State
					action { //it:State
						stepCounter = stepCounter + 1
						println("MAP BEFORE EXPLORE STEP $stepCounter")
						solve("direction(D)","") //set resVar	
						println("direction at start: ${getCurSol("D").toString()}")
						itunibo.planner.plannerUtil.showMap(  )
						itunibo.planner.plannerUtil.setGoal( "$stepCounter", "$stepCounter"  )
						if(stepCounter > 1)solve("dialog(F)","") //set resVar	
						itunibo.planner.moveUtils.doPlan(myself)
					}
					 transition( edgeName="goto",targetState="executePlannedActions", cond=doswitchGuarded({itunibo.planner.moveUtils.existPlan()}) )
					transition( edgeName="goto",targetState="endOfJob", cond=doswitchGuarded({! itunibo.planner.moveUtils.existPlan()}) )
				}	 
				state("executePlannedActions") { //this:State
					action { //it:State
						solve("retract(move(M))","") //set resVar	
						if(currentSolution.isSuccess()) {Curmove = getCurSol("M").toString(); curmoveIsForward=(Curmove == "w")}
						 		else{
						 			 Curmove = ""; curmoveIsForward=false
						 		}
					}
					 transition( edgeName="goto",targetState="checkAndDoAction", cond=doswitchGuarded({(Curmove.length>0) }) )
					transition( edgeName="goto",targetState="backToHome", cond=doswitchGuarded({! (Curmove.length>0) }) )
				}	 
				state("checkAndDoAction") { //this:State
					action { //it:State
					}
					 transition( edgeName="goto",targetState="doForwardMove", cond=doswitchGuarded({curmoveIsForward}) )
					transition( edgeName="goto",targetState="doTheMove", cond=doswitchGuarded({! curmoveIsForward}) )
				}	 
				state("doTheMove") { //this:State
					action { //it:State
						forward("modelChange", "modelChange(robot,$Curmove)" ,"resourcemodel" ) 
						itunibo.planner.moveUtils.doPlannedMove(myself ,Curmove )
						delay(RotateTime)
						forward("modelChange", "modelChange(robot,h)" ,"resourcemodel" ) 
						delay(PauseTime)
					}
					 transition( edgeName="goto",targetState="executePlannedActions", cond=doswitch() )
				}	 
				state("doForwardMove") { //this:State
					action { //it:State
						itunibo.planner.plannerUtil.startTimer(  )
						forward("onestep", "onestep($StepTime)" ,"onecellforward" ) 
					}
					 transition(edgeName="t00",targetState="handleStepOk",cond=whenDispatch("stepOk"))
					transition(edgeName="t01",targetState="hadleStepFail",cond=whenDispatch("stepFail"))
				}	 
				state("handleStepOk") { //this:State
					action { //it:State
						itunibo.planner.moveUtils.doPlannedMove(myself ,"w" )
						delay(PauseTime)
					}
					 transition( edgeName="goto",targetState="executePlannedActions", cond=doswitch() )
				}	 
				state("hadleStepFail") { //this:State
					action { //it:State
						var Tback = 0L
						println("MAP when hadleStepFail")
						itunibo.planner.plannerUtil.showMap(  )
						if( checkMsgContent( Term.createTerm("stepFail(R,T)"), Term.createTerm("stepFail(R,D)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								Tback=payloadArg(1).toString().toLong() 
								println(" ..................................  BACK TIME= $Tback")
						}
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("modelChange", "modelChange(robot,s)" ,"resourcemodel" ) 
						delay(Tback)
						forward("modelChange", "modelChange(robot,h)" ,"resourcemodel" ) 
						solve("direction(D)","") //set resVar	
						println("direction at fail: ${getCurSol("D").toString()}")
						itunibo.planner.plannerUtil.doMove( getCurSol("D").toString()  )
						delay(PauseTime)
					}
					 transition( edgeName="goto",targetState="backToHome", cond=doswitch() )
				}	 
				state("backToHome") { //this:State
					action { //it:State
						println("&&&  backToHome")
						solve("direction(D)","") //set resVar	
						println("direction at backToHome: ${getCurSol("D").toString()}")
						println("MAP BEFORE backToHome")
						itunibo.planner.plannerUtil.showMap(  )
						solve("retractall(move(_))","") //set resVar	
						itunibo.planner.plannerUtil.setGoal( 0, 0  )
						itunibo.planner.moveUtils.doPlan(myself)
					}
					 transition( edgeName="goto",targetState="doGoHomeActions", cond=doswitch() )
				}	 
				state("doGoHomeActions") { //this:State
					action { //it:State
						solve("retract(move(M))","") //set resVar	
						if(currentSolution.isSuccess()) Curmove = getCurSol("M").toString() 
						 		else{
						 			 Curmove = "" 
						 		}
						if(currentSolution.isSuccess()) forward("modelChange", "modelChange(robot,${getCurSol("M").toString()})" ,"resourcemodel" ) 
						if(currentSolution.isSuccess()) itunibo.planner.moveUtils.doPlannedMove(myself ,getCurSol("M").toString() )
						if(Curmove == "w" )delay(StepTime)
						 		else{
						 			 delay(RotateTime)
						 		}
						if(currentSolution.isSuccess()) forward("modelChange", "modelChange(robot,h)" ,"resourcemodel" ) 
						delay(PauseTime)
					}
					 transition( edgeName="goto",targetState="doGoHomeActions", cond=doswitchGuarded({(Curmove.length>0) }) )
					transition( edgeName="goto",targetState="doExploreStep", cond=doswitchGuarded({! (Curmove.length>0) }) )
				}	 
				state("endOfJob") { //this:State
					action { //it:State
						println("EXPLRATION ENDS")
					}
				}	 
			}
		}
}
