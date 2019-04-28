/* Generated by AN DISI Unibo */ 
package it.unibo.radarControl

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class RadarControl ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
protected var timerCount = 0         				 	//used by onMsg
protected var timerEventName = ""    					//used by onMsg


	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("radarControl init the GUI ... ")
						resources.radarSupport.create(  )
					}
					 transition( edgeName="goto",targetState="waitDataToShow", cond=doswitch() )
				}	 
				state("waitDataToShow") { //this:State
					action { //it:State
					}
					 transition(edgeName="t122",targetState="handleData",cond=whenEvent("polar"))
				}	 
				state("handleData") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("p(Distance,Angle)"), Term.createTerm("p(D,A)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								resources.radarSupport.sendData( "${meta_msgArg(0)}" ,"${meta_msgArg(1)}"  )
						}
					}
					 transition( edgeName="goto",targetState="waitDataToShow", cond=doswitch() )
				}	 
			}
		}
}
