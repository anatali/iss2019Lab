/* Generated by AN DISI Unibo */ 
package it.unibo.robotplayer

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Robotplayer ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("robotplayer STARTS")
						resources.robotSupport.create(myself ,"localhost" )
						resources.robotSupport.move( "msg(moveleft)"  )
						delay(1000) 
						resources.clientWenvObjTcp.sendMsg( "msg(moveright)"  )
					}
					 transition(edgeName="t00",targetState="handleCmd",cond=whenDispatch("robotCmd"))
					transition(edgeName="t01",targetState="handleCmd",cond=whenEvent("robotCmd"))
				}	 
				state("handleCmd") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("robotCmd(X)"), Term.createTerm("robotCmd(usercmd(CMD))"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("usercmd", "payloadArg(0)" ,"mind" ) 
						}
						 		else{
						 			 if( checkMsgContent( Term.createTerm("robotCmd(X)"), Term.createTerm("robotCmd(CMD)"), 
						 			                         currentMsg.msgContent()) ) { //set msgArgList
						 			 		resources.clientWenvObjTcp.sendMsg( "msg(${payloadArg(0)})"  )
						 			 }
						 		}
					}
					 transition(edgeName="t12",targetState="handleCmd",cond=whenEvent("robotCmd"))
				}	 
				state("handleSonar") { //this:State
					action { //it:State
						resources.clientWenvObjTcp.sendMsg( "msg(movebackward)"  )
						delay(1000) 
						resources.clientWenvObjTcp.sendMsg( "msg(stop)"  )
					}
					 transition( edgeName="goto",targetState="handleCmd", cond=doswitch() )
				}	 
			}
		}
}
