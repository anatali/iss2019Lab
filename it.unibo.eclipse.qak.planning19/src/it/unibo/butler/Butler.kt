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
		
		//var MaxDimY = "6"
		//var MaxDimX = "8"
		//var Curmove = ""
		//var curmoveIsForward = false
		//var Direction = "" 
		
		var StepTime   = 1000L	//long		//for real
		var RotateTime = 560L	//long		//for real 
		var PauseTime  = 1000L 
		var RotateStep = 255L	//long		//for real 
		var BackTime   = 500L
		
		//var StepTime   = 330L	//for virtual
		//var RotateTime = 300L	//for virtual
		//var PauseTime  = 100L 
		
		
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("&&&  butler STARTED")
						solve("consult('sysRules.pl')","") //set resVar	
						solve("consult('floorMap.pl')","") //set resVar	
						solve("showMap","") //set resVar	
					}
					 transition( edgeName="goto",targetState="exploreTheRoom", cond=doswitch() )
				}	 
				state("exploreTheRoom") { //this:State
					action { //it:State
					}
					 transition( edgeName="goto",targetState="moveAhead", cond=doswitch() )
				}	 
				state("moveAhead") { //this:State
					action { //it:State
						forward("onestep", "onestep($StepTime)" ,"butlerstep" ) 
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
								println(" ..................................  BACK TIME= $Tback")
						}
						solve("dialog(F)","") //set resVar	
						if( Tback > StepTime * 2 /3 ) Tback = 0 else Tback = Tback / 3 
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
						delay(PauseTime)
						forward("modelChange", "modelChange(robot,s)" ,"resourcemodel" ) 
						delay(BackTime)
						forward("modelChange", "modelChange(robot,h)" ,"resourcemodel" ) 
						delay(PauseTime)
					}
					 transition( edgeName="goto",targetState="endOfExploration", cond=doswitchGuarded({(getCurSol("D").toString() == "sud")}) )
					transition( edgeName="goto",targetState="tuning", cond=doswitchGuarded({! (getCurSol("D").toString() == "sud")}) )
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
					 transition( edgeName="goto",targetState="findTheTable", cond=doswitch() )
				}	 
				state("findTheTable") { //this:State
					action { //it:State
						println("findTheTable STARTS")
						
						val map = itunibo.planner.plannerUtil.getMap() 
						println(map)
					}
				}	 
			}
		}
}
