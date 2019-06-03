/* Generated by AN DISI Unibo */ 
package it.unibo.robotmvc

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Robotmvc ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("robotplayer STARTS")
						resources.robotSupport.create(myself ,"virtual" )
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
					}
					 transition(edgeName="t02",targetState="handleModelChanged",cond=whenEvent("modelChanged"))
					transition(edgeName="t03",targetState="handleCond",cond=whenEvent("envCond"))
					transition(edgeName="t04",targetState="handleCond",cond=whenEvent("sonarRobot"))
				}	 
				state("handleModelChanged") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("modelChanged(TARGET,VALUE)"), Term.createTerm("modelChanged(robot,CMD)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								resources.robotSupport.move( "msg(${payloadArg(1)})"  )
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("handleCond") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("modelChange", "modelChange(robot,h)" ,"resourcemodel" ) 
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
