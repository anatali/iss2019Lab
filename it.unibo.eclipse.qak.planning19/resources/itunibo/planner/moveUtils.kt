package itunibo.planner

import aima.core.agent.Action
import it.unibo.kactor.ActorBasic

object moveUtils{
    private val actions : List<Action>? = null
    private var existPlan = false
	
//    fun storeMoves( actor : ActorBasic ){
//        storeeMovesInActor( actor, plannerUtil.getActions()  ) 
//    }

    private fun storeeMovesInActor( actor : ActorBasic, actions : List<Action>?  ) {
        if( actions == null ) return
        val iter = actions!!.iterator()
        while (iter.hasNext()) {
            val a = iter.next()
            actor.solve("assert( move($a) )")
        }
		//actor.solve("assert( move(h) )")
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
		if( existPlan ) storeeMovesInActor(actor,plan) 
	}
	
	fun existPlan() : Boolean{
		return existPlan
	}
	fun doPlannedMove(actor : ActorBasic, move: String){
		plannerUtil.doMove( move )
		setDirection( actor )
	}
}
