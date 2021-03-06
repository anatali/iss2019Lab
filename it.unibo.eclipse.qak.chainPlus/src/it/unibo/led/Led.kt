/* Generated by AN DISI Unibo */ 
package it.unibo.led

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Led ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		var counter = 0
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						resources.myLedSegm.create()
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
						println("led waits ...")
						stateTimer = TimerActor("timer_waitCmd", scope, context!!, "local_tout_led_waitCmd", 5000.toLong())
					}
					 transition(edgeName="t05",targetState="tooLate",cond=whenTimeout("local_tout_led_waitCmd"))   
					transition(edgeName="t06",targetState="handleLedCmd",cond=whenDispatchGuarded("ledCmd",{counter++ < 5 || counter > 8}))
				}	 
				state("handleLedCmd") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("ledCmd(X)"), Term.createTerm("ledCmd(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("led handleLedCmd: ${payloadArg(0)} counter=$counter")
						}
						if( checkMsgContent( Term.createTerm("ledCmd(X)"), Term.createTerm("ledCmd(on)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								resources.myLedSegm.turnOn()
						}
						if( checkMsgContent( Term.createTerm("ledCmd(X)"), Term.createTerm("ledCmd(off)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								resources.myLedSegm.turnOff()
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("tooLate") { //this:State
					action { //it:State
						println("Be faster, please ... ")
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
