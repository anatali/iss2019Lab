package planner

import aima.core.agent.Action;

object plannerSupport {
	fun demo(){
		println("plannerSupport demo1")
		try {
			aiutil.initAI();
			aiutil.cleanQa();
			println("===== initial map");
			aiutil.showMap();
			doSomeMOve();
			println("===== map after some move");
			aiutil.showMap();
			val actions : MutableList<Action> = aiutil.doPlan();
			println("===== plan actions: " + actions);
			executeMoves( actions );
			println("===== map after plan");
			aiutil.showMap();
		} catch (  e : Exception) {
 			e.printStackTrace();
		}		
		println("plannerSupport demo2")
		
	}
	
	fun doSomeMOve(){
		aiutil.doMove("w");
		aiutil.doMove("a");
		aiutil.doMove("w");
		aiutil.doMove("w");
		aiutil.doMove("d");
		aiutil.doMove("w");
		aiutil.doMove("a");
		aiutil.doMove("obstacleOnRight");		
	}
	
	fun executeMoves(actions : MutableList<Action> )   {
		val iter = actions.iterator() //Iterator<Action> 
		while( iter.hasNext() ) {
			aiutil.doMove(iter.next().toString());
		}
	}
	
}