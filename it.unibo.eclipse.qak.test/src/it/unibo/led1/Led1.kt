/* Generated by AN DISI Unibo */ 
package it.unibo.led1

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Led1 ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
protected var timerCount = 0         				 	//used by onMsg
protected var timerEventName = ""    					//used by onMsg


	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						resources.myLedSegm.create(  )
					}
					 transition(edgeName="ta20",targetState="s1",cond=whenEvent("ledCmd"))
				}	 
				state("s1") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("ledCmd(X)"), Term.createTerm("ledCmd(on)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								resources.myLedSegm.turnOn(  )
						}
						if( checkMsgContent( Term.createTerm("ledCmd(X)"), Term.createTerm("ledCmd(off)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								resources.myLedSegm.turnOff(  )
						}
					}
					 transition(edgeName="tb21",targetState="s1",cond=whenEvent("ledCmd"))
				}	 
			}
		}
}
