/* Generated by AN DISI Unibo */ 
package it.unibo.robotreq

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Robotreq ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("robotplayer STARTS")
						resources.robotSupport.create(myself ,"virtual" )
						resources.robotSupport.move("msg(a)" )
						delay(1000) 
						resources.robotSupport.move("msg(d)" )
						delay(1000) 
						resources.robotSupport.move("msg(h)" )
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
					}
					 transition(edgeName="t01",targetState="handleCmd",cond=whenDispatch("robotCmd"))
					transition(edgeName="t02",targetState="handleCond",cond=whenEvent("envCond"))
				}	 
				state("handleCmd") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("robotCmd(X)"), Term.createTerm("robotCmd(CMD)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								resources.robotSupport.move("msg(${payloadArg(0)})" )
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("handleCond") { //this:State
					action { //it:State
						resources.robotSupport.move("msg(stop)" )
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}