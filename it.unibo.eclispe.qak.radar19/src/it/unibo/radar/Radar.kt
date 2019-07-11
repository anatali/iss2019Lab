/* Generated by AN DISI Unibo */ 
package it.unibo.radar

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Radar ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "radarUsageInit"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
		var LastData = 0
		var CurData  = 0
		var ShowData = false
		return { //this:ActionBasciFsm
				state("radarUsageInit") { //this:State
					action { //it:State
						resources.radarSupport.activate(  )
						solve("consult('radarData.pl')","") //set resVar	
					}
					 transition( edgeName="goto",targetState="waitMsg", cond=doswitch() )
				}	 
				state("radarTest") { //this:State
					action { //it:State
						solve("getData(D,A)","") //set resVar	
						if(currentSolution.isSuccess()) { resources.radarSupport.spot( getCurSol("D").toString(), getCurSol("A").toString()  )
						 }
						delay(500) 
					}
					 transition( edgeName="goto",targetState="radarTest", cond=doswitchGuarded({currentSolution.isSuccess()}) )
					transition( edgeName="goto",targetState="waitMsg", cond=doswitchGuarded({! currentSolution.isSuccess()}) )
				}	 
				state("waitMsg") { //this:State
					action { //it:State
					}
					 transition(edgeName="t00",targetState="showPoint",cond=whenDispatch("polar"))
					transition(edgeName="t01",targetState="showPoint",cond=whenEvent("polar"))
				}	 
				state("showPoint") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("p(Distance,Angle)"), Term.createTerm("p(D,A)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
										CurData       = Integer.parseInt( payloadArg(0)  )
										//println(  "${CurData}" ) 
										val D : Int   = CurData/3
										ShowData      = Math.abs( CurData - LastData ) > 5  
										if(ShowData){
											 println(  "-------------------- ${D}" )
											 LastData = CurData
										}
								if(ShowData){ resources.radarSupport.spot( "$D", payloadArg(1)  )
								 }
						}
					}
					 transition( edgeName="goto",targetState="waitMsg", cond=doswitch() )
				}	 
			}
		}
}
