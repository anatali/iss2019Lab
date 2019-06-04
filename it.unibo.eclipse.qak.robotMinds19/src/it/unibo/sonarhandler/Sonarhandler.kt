/* Generated by AN DISI Unibo */ 
package it.unibo.sonarhandler

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Sonarhandler ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "init"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		var LastSonarDistance = 0
		  val amplify=5
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						println("sonarhandler STARTS ... ")
					}
					 transition( edgeName="goto",targetState="waitForEvents", cond=doswitch() )
				}	 
				state("waitForEvents") { //this:State
					action { //it:State
					}
					 transition(edgeName="t04",targetState="handleSonar",cond=whenEvent("sonar"))
					transition(edgeName="t05",targetState="handleSonar",cond=whenEvent("sonarRobot"))
				}	 
				state("handleSonar") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("sonar(SONAR,DISTANCE)"), Term.createTerm("sonar(SONAR,DISTANCE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 val D = Integer.parseInt( payloadArg(1) ) * amplify
								emit("polar", "p($D,90)" ) 
						}
						if( checkMsgContent( Term.createTerm("sonar(DISTANCE)"), Term.createTerm("sonar(DISTANCE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 val D = Integer.parseInt( payloadArg(0) ) * amplify
								emit("polar", "p($D,180)" ) 
								if(LastSonarDistance  != D)forward("modelChange", "modelChange(sonarRobot,${payloadArg(0)})" ,"resourcemodel" ) 
								if(LastSonarDistance  != D)LastSonarDistance=D
						}
					}
					 transition( edgeName="goto",targetState="waitForEvents", cond=doswitch() )
				}	 
			}
		}
}
