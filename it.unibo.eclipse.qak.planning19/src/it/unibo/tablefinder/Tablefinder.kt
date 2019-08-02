/* Generated by AN DISI Unibo */ 
package it.unibo.tablefinder

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Tablefinder ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
		var mapEmpty    = true
		val inmapname   = "roomMbot" //   "roomBoundary"	 
		val outmapname  = "roomMbotWithTable" //roomMapWithTable		 
		var Tback       = 0
		 
		var Curmove     = ""
		var Direction   = "" 
		var MaxX        = 0
		var MaxY        = 0
		var CurX        = 0
		var CurY        = 0
		 
		var curmoveIsForward = false
		var stepRoundTable   = 0
		//REAL ROBOT
		var StepTime   = 1000	 
		var PauseTime  = 500
		
		//VIRTUAL ROBOT
		//var StepTime   = 330	 
		//var PauseTime  = 250
		
		var PauseTimeL  = PauseTime.toLong()
		
		
		var ButlerPosX  = 0 
		var ButlerPosY  = 0 
		var FoundWall   = false
		var DimMapX     = 0
		var DimMapY     = 0
		var CenterX     = 0   
		var CenterY     = 0
		
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						solve("consult('moves.pl')","") //set resVar	
						itunibo.planner.plannerUtil.initAI(  )
						itunibo.planner.moveUtils.loadRoomMap(myself ,inmapname )
						mapEmpty = itunibo.planner.moveUtils.mapIsEmpty()	
						if(! mapEmpty){ 	val MapStr =  itunibo.planner.plannerUtil.getMapOneLine()  
										println(MapStr)
						forward("modelUpdate", "modelUpdate(roomMap,$MapStr)" ,"resourcemodel" ) 
						 }
					}
					 transition( edgeName="goto",targetState="warning", cond=doswitchGuarded({mapEmpty}) )
					transition( edgeName="goto",targetState="findTable", cond=doswitchGuarded({! mapEmpty}) )
				}	 
				state("warning") { //this:State
					action { //it:State
						println("========================================")
						println("WARNING: map not found")
						println("Please run roomboundaryplanned")
						println("========================================")
					}
				}	 
				state("findTable") { //this:State
					action { //it:State
						
						DimMapX     = itunibo.planner.moveUtils.getMapDimX()
						DimMapY     = itunibo.planner.moveUtils.getMapDimY()
						CenterX     = (DimMapX-1) / 2
						CenterY     = (DimMapY-1) / 2
						println("%%% tablefinder: findind Table  goal=$CenterX, $CenterY")
						forward("moveButlerTo", "moveButlerTo($CenterX,$CenterY,eastDir)" ,"worker" ) 
					}
					 transition(edgeName="t00",targetState="strange",cond=whenDispatch("stepOk"))
					transition(edgeName="t01",targetState="tableAsObstacleFound",cond=whenDispatch("stepFail"))
				}	 
				state("strange") { //this:State
					action { //it:State
						println("%%% tablefinder: strange")
					}
				}	 
				state("tableAsObstacleFound") { //this:State
					action { //it:State
						println("%%% tablefinder: tableAsObstacleFound")
					}
				}	 
			}
		}
}
