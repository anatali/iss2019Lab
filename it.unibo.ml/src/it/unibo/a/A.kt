/* Generated by AN DISI Unibo */ 
package it.unibo.a

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class A ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						solve("consult('sysRules.pl')","") //set resVar	
						solve("consult('neuronConnKb.pl')","") //set resVar	
						solve("setConnection(c,1)","") //set resVar	
						solve("prepareConnectionsToSend","") //set resVar	
					}
					 transition( edgeName="goto",targetState="propagateOutput", cond=doswitch() )
				}	 
				state("propagateOutput") { //this:State
					action { //it:State
						solve("retract(link(N,I))","") //set resVar	
						if(currentSolution.isSuccess()) { 
										val Dest  = currentSolution.getVarValue("N").toString()
										val Input = currentSolution.getVarValue("I").toString()
										//println(" a propagateOutput dest = $Dest, input = $Input ")
										forward("son", "son($Input)" ,Dest ) 		 
						 }
					}
					 transition( edgeName="goto",targetState="propagateOutput", cond=doswitchGuarded({currentSolution.isSuccess()}) )
					transition( edgeName="goto",targetState="end", cond=doswitchGuarded({! currentSolution.isSuccess()}) )
				}	 
				state("end") { //this:State
					action { //it:State
						println("neuron a ends")
					}
				}	 
			}
		}
}