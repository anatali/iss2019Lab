/* Generated by AN DISI Unibo */ 
package it.unibo.stimulator

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Stimulator ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("work") { //this:State
					action { //it:State
						println("stimulator signal( i1, on )")
						forward("signal", "signal(i1,on)" ,"aneuron" ) 
						delay(500) 
						forward("signal", "signal(i2,on)" ,"aneuron" ) 
						delay(500) 
						forward("signal", "signal(i3,on)" ,"aneuron" ) 
						delay(500) 
						forward("signal", "signal(i1,off)" ,"aneuron" ) 
						delay(500) 
						forward("signal", "signal(i2,off)" ,"aneuron" ) 
						delay(500) 
						forward("signal", "signal(i3,off)" ,"aneuron" ) 
					}
				}	 
			}
		}
}
