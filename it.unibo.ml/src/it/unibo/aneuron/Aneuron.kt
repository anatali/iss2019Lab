/* Generated by AN DISI Unibo */ 
package it.unibo.aneuron

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Aneuron ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
			var i1 = false
			var i2 = false
			var i3 = false
			var firing = false
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("aneuron idle")
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("work") { //this:State
					action { //it:State
					}
					 transition(edgeName="t00",targetState="handleSsignal",cond=whenDispatch("signal"))
				}	 
				state("handleSsignal") { //this:State
					action { //it:State
						 var value = false 
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("signal(INPUT,STATE)"), Term.createTerm("signal(_,V)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 value=payloadArg(1).equals("on")
						}
						if( checkMsgContent( Term.createTerm("signal(INPUT,STATE)"), Term.createTerm("signal(i1,V)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 i1 = value
						}
						if( checkMsgContent( Term.createTerm("signal(INPUT,STATE)"), Term.createTerm("signal(i2,V)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 i2 = value
						}
						if( checkMsgContent( Term.createTerm("signal(INPUT,STATE)"), Term.createTerm("signal(i3,V)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 i3 = value
						}
					}
					 transition( edgeName="goto",targetState="elab", cond=doswitch() )
				}	 
				state("elab") { //this:State
					action { //it:State
						println("i1=$i1 i2=$i2 i3=$i3")
						if((i1 and i2) or (i1 and i3) or (i2 and i3)){ if(! firing){ firing = true
						println("NEURON FIRE")
						 }
						else
						 { println("NEURON ALREADY FIRING")
						  }
						 }
						else
						 { firing = false
						 println("NEURON IDLE")
						  }
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
			}
		}
}
