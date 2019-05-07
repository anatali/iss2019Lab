/* Generated by AN DISI Unibo */ 
package it.unibo.control

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Control ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		lateinit var ledNames : List<String>
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						solve("consult('sysRules.pl')","") //set resVar	
						solve("consult('chainDescr.pl')","") //set resVar	
						solve("getLedNames(N)","N") //set resVar	
						ledNames = sysUtil.strRepToList(resVar!!); println(ledNames)
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("work") { //this:State
					action { //it:State
						println(" ----------------- work ---------------------- ")
					}
					 transition(edgeName="t00",targetState="blinkChain",cond=whenEvent("local_buttonCmd"))
					transition(edgeName="t01",targetState="addLed",cond=whenDispatch("ledRegister"))
				}	 
				state("blinkChain") { //this:State
					action { //it:State
						solve("resetLedCounter","") //set resVar	
					}
					 transition( edgeName="goto",targetState="doBlinkChain", cond=doswitch() )
				}	 
				state("doBlinkChain") { //this:State
					action { //it:State
						solve("getNextLedName(LEDNAME)","LEDNAME") //set resVar	
						 
						if( solveOk() ){
						    println("current led name = $resVar")   
							forward( "ledCmd", "ledCmd(on)", resVar )
							delay(200)
							forward( "ledCmd", "ledCmd(off)", resVar )
						}
						stateTimer = TimerActor("timer_doBlinkChain", scope, context!!, "local_tout_control_doBlinkChain", 200.toLong())
					}
					 transition(edgeName="t12",targetState="doBlinkChain",cond=whenTimeout("local_tout_control_doBlinkChain"))   
					transition(edgeName="t13",targetState="addLed",cond=whenDispatch("ledRegister"))
					transition(edgeName="t14",targetState="removeLed",cond=whenDispatch("ledUnRegister"))
					transition(edgeName="t15",targetState="work",cond=whenEvent("local_buttonCmd"))
				}	 
				state("addLed") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("ledRegister(LEDNAME,CTXNAME)"), Term.createTerm("ledRegister(LEDNAME,CTXNAME)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("control ADDING ${meta_msgArg(0)} from CONTEXT ${meta_msgArg(1)}")
								solve("addLed('${meta_msgArg(0)}')","") //set resVar	
								sysUtil.setActorContextName(meta_msgArg(0), meta_msgArg(1))  //update system knowledge
						}
					}
					 transition( edgeName="goto",targetState="blinkChain", cond=doswitch() )
				}	 
				state("removeLed") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("ledUnRegister(LEDNAME,CTXNAME)"), Term.createTerm("ledUnRegister(LEDNAME,CTXNAME)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("REMOVING ${meta_msgArg(0)}")
								solve("removeLed('${meta_msgArg(0)}')","") //set resVar	
						}
					}
					 transition( edgeName="goto",targetState="blinkChain", cond=doswitch() )
				}	 
			}
		}
}
