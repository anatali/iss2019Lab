/* Generated by AN DISI Unibo */ 
package it.unibo.dynamo

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Dynamo ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("dynamo START")
						stateTimer = TimerActor("timer_s0", scope, context!!, "local_tout_dynamo_s0", 3000.toLong())
					}
					 transition(edgeName="t06",targetState="sAdd",cond=whenTimeout("local_tout_dynamo_s0"))   
				}	 
				state("sAdd") { //this:State
					action { //it:State
						for(i in 0..1){  
							val ledName = resources.ledManager.addNewLed( myself  ) 
							println("ledRegister($ledName, ${context!!.name})")
							forward( "ledRegister", "ledRegister($ledName, ${context!!.name})", "control" )
						    delay( 1000 ) 
						}
						stateTimer = TimerActor("timer_sAdd", scope, context!!, "local_tout_dynamo_sAdd", 3000.toLong())
					}
					 transition(edgeName="t07",targetState="sRemove",cond=whenTimeout("local_tout_dynamo_sAdd"))   
				}	 
				state("sRemove") { //this:State
					action { //it:State
						val led = resources.ledManager.removeLastLed( myself  ) 
						if( led != null ){ 
						  println("ledUnRegister(${led.name}, ${context!!.name})")
						  forward( "ledUnRegister", "ledUnRegister(${led.name}, ${context!!.name} )", "control" )
						  delay( 1000 )
						  led.terminate()     //please complete (normally) what you were doing and terminate
						}
					}
				}	 
			}
		}
}
