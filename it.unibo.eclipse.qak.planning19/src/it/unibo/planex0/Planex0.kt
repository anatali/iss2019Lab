/* Generated by AN DISI Unibo */ 
package it.unibo.planex0

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Planex0 ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
		var Curmove     = ""  
		var IterCounter = 0
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("&&&  planex0 STARTED")
						itunibo.planner.plannerUtil.initAI(  )
						println("INITIAL MAP")
						itunibo.planner.plannerUtil.showMap(  )
						itunibo.planner.plannerUtil.setGoal( "2", "2"  )
						itunibo.planner.moveUtils.doPlan(myself)
					}
					 transition( edgeName="goto",targetState="executePlannedActions", cond=doswitch() )
				}	 
				state("executePlannedActions") { //this:State
					action { //it:State
						solve("retract(move(M))","") //set resVar	
						if(currentSolution.isSuccess()) {Curmove = getCurSol("M").toString()}
						 		else{
						 			 Curmove="nomove" 
						 		}
						if(currentSolution.isSuccess()) itunibo.planner.moveUtils.doPlannedMove(myself ,Curmove )
					}
					 transition( edgeName="goto",targetState="executePlannedActions", cond=doswitchGuarded({(Curmove != "nomove")}) )
					transition( edgeName="goto",targetState="nextStep", cond=doswitchGuarded({! (Curmove != "nomove")}) )
				}	 
				state("nextStep") { //this:State
					action { //it:State
						println("MAP after step $IterCounter")
						itunibo.planner.plannerUtil.showMap(  )
						IterCounter++
					}
					 transition( edgeName="goto",targetState="endOfJob", cond=doswitchGuarded({(IterCounter==2)}) )
					transition( edgeName="goto",targetState="donextStep", cond=doswitchGuarded({! (IterCounter==2)}) )
				}	 
				state("donextStep") { //this:State
					action { //it:State
						itunibo.planner.plannerUtil.setGoal( "1", "1"  )
						itunibo.planner.moveUtils.doPlan(myself)
					}
					 transition( edgeName="goto",targetState="executePlannedActions", cond=doswitch() )
				}	 
				state("endOfJob") { //this:State
					action { //it:State
						println("FINAL MAP")
						itunibo.planner.plannerUtil.showMap(  )
						println("&&&  planex0 ENDS")
					}
				}	 
			}
		}
}