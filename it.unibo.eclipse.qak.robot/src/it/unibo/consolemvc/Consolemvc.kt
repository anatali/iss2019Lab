/* Generated by AN DISI Unibo */ 
package it.unibo.consolemvc

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Consolemvc ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						resources.guiMvcSupport.create(myself ,"" )
						solve("consult('sysRules.pl')","") //set resVar	
						solve("consult('ddrworkermvc.pl')","") //set resVar	
						solve("showSystemConfiguration","") //set resVar	
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
					}
					 transition(edgeName="t00",targetState="handleUserCmd",cond=whenEvent("local_userCmd"))
				}	 
				state("handleUserCmd") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("userCmd(X)"), Term.createTerm("userCmd(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("modelChange", "modelChange(robot,${payloadArg(0)})" ,"resourcemodel" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
