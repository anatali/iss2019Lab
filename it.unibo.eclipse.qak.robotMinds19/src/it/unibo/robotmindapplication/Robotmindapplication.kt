/* Generated by AN DISI Unibo */ 
package it.unibo.robotmindapplication

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Robotmindapplication ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
		//REAL ROBOT
		//var StepTime   = 1000	 
		//var PauseTime  = 500L 
		
		//VIRTUAL ROBOT
		var StepTime   = 330	//330	 
		var PauseTime  = 250  
		
		var StepTimeL  = StepTime.toLong()
		var PauseTimeL = PauseTime.toLong()
		
		var newDir = ""
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						solve("consult('sysRules.pl')","") //set resVar	
						solve("consult('floorMap.pl')","") //set resVar	
						
						val hostAddr = "coap://localhost:5683" 	// 192.168.43.67:5683
						val coapclient   = org.eclipse.californium.core.CoapClient( "${hostAddr}/resourcemodel")
						val relation = coapclient.observe(  itunibo.coap.observer.Listener )  //CoapHandler
					}
					 transition( edgeName="goto",targetState="startApplication", cond=doswitch() )
				}	 
				state("startApplication") { //this:State
					action { //it:State
						solve("initMap(sud)","") //set resVar	
					}
					 transition( edgeName="goto",targetState="doApplication", cond=doswitch() )
				}	 
				state("doApplication") { //this:State
					action { //it:State
						forward("onestep", "onestep($StepTime)" ,"onestepahead" ) 
					}
					 transition(edgeName="t00",targetState="hadleStepOk",cond=whenDispatch("stepOk"))
					transition(edgeName="t01",targetState="hadleStepFail",cond=whenDispatch("stepFail"))
				}	 
				state("hadleStepOk") { //this:State
					action { //it:State
						solve("updateMapAfterStep","") //set resVar	
						delay(PauseTimeL)
					}
					 transition( edgeName="goto",targetState="doApplication", cond=doswitch() )
				}	 
				state("hadleStepFail") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("modelChange", "modelChange(robot,a)" ,"resourcemodel" ) 
						delay(PauseTimeL)
						solve("changeDirection(NEWD)","") //set resVar	
						newDir = getCurSol("NEWD").toString()
						println("New direction=${newDir}")
						solve("showMap","") //set resVar	
						delay(PauseTimeL)
					}
					 transition( edgeName="goto",targetState="endOfJob", cond=doswitchGuarded({(newDir.equals( "sud" ) )}) )
					transition( edgeName="goto",targetState="doApplication", cond=doswitchGuarded({! (newDir.equals( "sud" ) )}) )
				}	 
				state("endOfJob") { //this:State
					action { //it:State
						println("Exploration done")
					}
				}	 
			}
		}
}
