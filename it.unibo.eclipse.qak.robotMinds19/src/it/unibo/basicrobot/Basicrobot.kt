/* Generated by AN DISI Unibo */ 
package it.unibo.basicrobot

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Basicrobot ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						  
						//CREATE A PIPE for the sonar-data stream
						val filter = itunibo.robot.sonaractorfilter( "sonaractorfilter" , myself  ) 
						val obstacleDetector =  itunibo.robot.obstacledetector( "obstacledetector" , myself   )
						filter.subscribe(obstacleDetector) 
						obstacleDetector.subscribe(sysUtil.getActor("onestepahead")!!)
						val logger = itunibo.robot.Logger("logFiltered")
						filter.subscribe(logger)  
						solve("consult('basicRobotConfig.pl')","") //set resVar	
						solve("robot(R,PORT)","") //set resVar	
						if(currentSolution.isSuccess()) { println("USING ROBOT : ${getCurSol("R")},  port= ${getCurSol("PORT")} ")
						itunibo.robot.robotSupport.create(myself ,getCurSol("R").toString(), getCurSol("PORT").toString(), filter )
						 }
						else
						{ println("no robot")
						 }
						itunibo.robot.robotSupport.move( "msg(a)"  )
						delay(700) 
						itunibo.robot.robotSupport.move( "msg(d)"  )
						delay(700) 
						itunibo.robot.robotSupport.move( "msg(h)"  )
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
					}
					 transition(edgeName="t011",targetState="handleRobotCmd",cond=whenDispatch("robotCmd"))
					transition(edgeName="t012",targetState="emitEventForMind",cond=whenEvent("obstacle"))
				}	 
				state("handleRobotCmd") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("robotCmd(CMD)"), Term.createTerm("robotCmd(MOVE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								itunibo.robot.robotSupport.move( "msg(${payloadArg(0)})"  )
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("emitEventForMind") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("obstacle(DISTANCE)"), Term.createTerm("obstacle(D)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								emit("obstacle", "obstacle(${payloadArg(0)})" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
