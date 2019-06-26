/* Generated by AN DISI Unibo */ 
package it.unibo.butler

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Butler ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
		var PauseTime  = 1000L 
		
		//var StepTime   = 1000L	
		//var RotateTime = 560L	 
		  
		var BackTime   = 500L
		
		var StepTime   = 330L	//for virtual
		var RotateTime = 300L	//for virtual
		
		
		var RobotDirection = ""
		
		
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("&&&  butler STARTED")
						solve("consult('sysRules.pl')","") //set resVar	
						solve("consult('floorMap.pl')","") //set resVar	
						solve("showMap","") //set resVar	
					}
					 transition( edgeName="goto",targetState="moveAhead", cond=doswitch() )
				}	 
				state("moveAhead") { //this:State
					action { //it:State
						forward("onestep", "onestep($StepTime)" ,"onestepahead" ) 
					}
					 transition(edgeName="t00",targetState="hadleStepOk",cond=whenDispatch("stepOk"))
					transition(edgeName="t01",targetState="hadleStepKo",cond=whenDispatch("stepFail"))
				}	 
				state("hadleStepOk") { //this:State
					action { //it:State
						solve("updateMapAfterStep","") //set resVar	
						solve("showMap","") //set resVar	
						delay(PauseTime)
					}
					 transition( edgeName="goto",targetState="moveAhead", cond=doswitch() )
				}	 
				state("hadleStepKo") { //this:State
					action { //it:State
						var Tback = 0L
						println("$name in ${currentState.stateName} | $currentMsg")
						println("&&& moveAhead failed")
						if( checkMsgContent( Term.createTerm("stepFail(R,T)"), Term.createTerm("stepFail(R,D)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
								Tback=payloadArg(1).toString().toLong() 
								if( Tback > StepTime * 2 /3 ) Tback = 0 else Tback = Tback / 3
								println(" ..................................  BACK TIME= $Tback")
						}
						if(Tback>0){ forward("modelChange", "modelChange(robot,s)" ,"resourcemodel" ) 
						delay(Tback)
						forward("modelChange", "modelChange(robot,h)" ,"resourcemodel" ) 
						 }
						else
						 { solve("updateMapAfterStep","") //set resVar	
						  }
						solve("showMap","") //set resVar	
						delay(PauseTime)
						forward("modelChange", "modelChange(robot,l)" ,"resourcemodel" ) 
						solve("changeDirection","") //set resVar	
						solve("robotdirection(D)","") //set resVar	
						RobotDirection = getCurSol("D").toString()
						println("RobotDirection= ${RobotDirection}")
						solve("dialog(F)","") //set resVar	
					}
					 transition( edgeName="goto",targetState="endOfExploration", cond=doswitchGuarded({(RobotDirection == "sud")}) )
					transition( edgeName="goto",targetState="tuning", cond=doswitchGuarded({! (RobotDirection == "sud")}) )
				}	 
				state("tuning") { //this:State
					action { //it:State
						println(" ---- TUNING --- ")
					}
					 transition( edgeName="goto",targetState="moveAhead", cond=doswitch() )
				}	 
				state("endOfExploration") { //this:State
					action { //it:State
						println("EXPLORATION ENDS")
					}
				}	 
			}
		}
}
