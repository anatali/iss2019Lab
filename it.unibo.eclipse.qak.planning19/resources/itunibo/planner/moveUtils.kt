package itunibo.planner

import aima.core.agent.Action
import it.unibo.kactor.ActorBasic

object moveUtils{
    private val actions : List<Action>? = null
    private var existPlan = false
	
    private fun storeMovesInActor( actor : ActorBasic, actions : List<Action>?  ) {
        if( actions == null ) return
        val iter = actions!!.iterator()
        while (iter.hasNext()) {
            val a = iter.next()
            actor.solve("assert( move($a) )")
        }
		//actor.solve("assert( move(h) )")
    }
	
	fun loadRoomMap( actor : ActorBasic,  fname : String ){
		val dims = plannerUtil.loadRoomMap( fname )
		actor.solve("retract( mapdims(_,_) )")		//remove old data
		actor.solve("assert(  mapdims( ${dims.first},${dims.second} ) )")
		
	}
	fun setDuration( actor : ActorBasic ){
		val time = plannerUtil.getDuration()
		actor.solve("retract( wduration(_) )")		//remove old data
		actor.solve("assert( wduration($time) )")
 	}
	
	fun setDirection( actor : ActorBasic ){
		val direction = plannerUtil.getDirection()
		actor.solve("retract( direction(_) )")		//remove old data
		actor.solve("assert( direction($direction) )")		
	}
	
	fun doPlan(actor : ActorBasic ){
		val plan = plannerUtil.doPlan(  )
		existPlan = plan != null
		if( existPlan ) storeMovesInActor(actor,plan) 
	}
	
	fun existPlan() : Boolean{
		return existPlan
	}
	fun doPlannedMove(actor : ActorBasic, move: String){
		plannerUtil.doMove( move )
		setDirection( actor )
	}
	
	fun setPosition(actor : ActorBasic){
		val posx = plannerUtil.getPosX()
		val posy = plannerUtil.getPosY()
		val direction = plannerUtil.getDirection()
		println("robot curPos=($posx,$posy,$direction)")
		actor.solve("retract( curPos(_,_) )")		//remove old data
		actor.solve("assert( curPos($posx,$posy) )")			
		actor.solve("retract( curPos(_,_,_) )")		//remove old data
		actor.solve("assert( curPos($posx,$posy,$direction) )")			
	}
}
