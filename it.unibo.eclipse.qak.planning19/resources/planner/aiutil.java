package planner;

import java.util.ArrayList;
import java.util.List;
import aima.core.agent.Action;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.problem.GoalTest;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import it.unibo.exploremap.stella.model.Box;
import it.unibo.exploremap.stella.model.Functions;
import it.unibo.exploremap.stella.model.RobotAction;
import it.unibo.exploremap.stella.model.RobotState;
import it.unibo.exploremap.stella.model.RoomMap;
import it.unibo.exploremap.stella.model.RobotState.Direction;
 

public class aiutil {
private static RobotState initialState;
		
/*
 * ------------------------------------------------
 * PLANNING	
 * ------------------------------------------------
 */
	private static BreadthFirstSearch search ;
	
	public static void initAI() throws Exception {
		System.out.println("aiutil initAI" );
 		initialState = new RobotState(0, 0, RobotState.Direction.DOWN);
		search = new BreadthFirstSearch(new GraphSearch());
	}
	
	public static void cleanQa() throws  Exception {
		System.out.println("aiutil cleanQa" );
		setGoalInit();
 		RoomMap.getRoomMap().setDirty();
	}
	 
	public static void cell0DirtyForHome() throws  Exception {
		RoomMap.getRoomMap().put(0, 0, new Box(false, true, false));
	}
	
	public static GoalTest goalTest;
	
	public static List<Action> doPlan(   ) throws Exception {
 		List<Action> actions;
// 		GoalTest goalTest= new Functions();
		SearchAgent searchAgent;
 //		System.out.println("aiutil doPlan newProblem (A) " );
		Problem problem = new Problem(initialState, new Functions(), new Functions(), goalTest, new Functions());
//		System.out.println("aiutil doPlan newProblem (A) search " );
		searchAgent = new SearchAgent(problem, search);
		actions     = searchAgent.getActions();
		if (actions == null || actions.isEmpty()) {
			System.out.println("aiutil doPlan NO MOVES !!!!!!!!!!!! " + actions );
			if (!RoomMap.getRoomMap().isClean()) RoomMap.getRoomMap().setObstacles();
			actions = new ArrayList<Action>();
//			qa.addRule("cleanFinished"); //by AN
			return null;
		}else if(actions.get(0).isNoOp() ) {
			System.out.println("aiutil doPlan NoOp" );	
//			qa.addRule("endOfWork"); //by AN (optimized target)
			return null;
		}
		System.out.println("aiutil doPlan actions=" + actions);	
//		if( actions.size() > 2  ) qa.solveGoal("logMove( plan(" + actions + "))");
//		Iterator<Action> iter = actions.iterator();
//		while( iter.hasNext() ) {
// 			String s = iter.next().toString();
//  			//System.out.println("aiutil doPlan assertz:" + s);
//			qa.solveGoal("assertz( move(" + s + "))");
//		}		
		return actions;		
	}

/*
* ------------------------------------------------
* MIND MAP UPDATE	
* ------------------------------------------------
*/
 
	
 
	public static void doMove( String move  ) throws Exception {
		Direction dir = initialState.getDirection();
  		int dimMapx   = RoomMap.getRoomMap().getDimX();
		int dimMapy   = RoomMap.getRoomMap().getDimY();
		int x         = initialState.getX();
		int y         = initialState.getY();
		System.out.println("aistruct: doMove move=" +  
				move + " dir=" + dir +" x=" + x + " y="+y + " dimMapX=" + dimMapx + " dimMapY=" + dimMapy   );
		try {
			switch( move ){
    	 	case "w" :   
		  		RoomMap.getRoomMap().put(x, y, new Box(false, false, false)); //clean the cell
 			  	initialState = (RobotState) new Functions().result(initialState, new RobotAction(RobotAction.FORWARD));
			  	RoomMap.getRoomMap().put(initialState.getX(), initialState.getY(), new Box(false, false, true));
 	 			break ;
	 		case "s" :   
				initialState = (RobotState) new Functions().result(initialState, new RobotAction(RobotAction.BACKWARD));
				RoomMap.getRoomMap().put(initialState.getX(), initialState.getY(), new Box(false, false, true));
				break ;
			case "a" : 
 	 			initialState = (RobotState) new Functions().result(initialState, new RobotAction(RobotAction.TURNLEFT));
	 			RoomMap.getRoomMap().put(initialState.getX(), initialState.getY(), new Box(false, false, true));
				break ;
			case "d" :  
	 			initialState = (RobotState) new Functions().result(initialState, new RobotAction(RobotAction.TURNRIGHT));
	 			RoomMap.getRoomMap().put(initialState.getX(), initialState.getY(), new Box(false, false, true));
				break ;
			case "c" :  	//forward and  clean 	
				RoomMap.getRoomMap().put(x, y, new Box(false, false, false));
				initialState = (RobotState) new Functions().result(initialState, new RobotAction(RobotAction.FORWARD));
				RoomMap.getRoomMap().put(initialState.getX(), initialState.getY(), new Box(false, false, true));
				break ; 
			case "obstacleOnRight" :   
				RoomMap.getRoomMap().put(x+1, y, new Box(true, false, false));
				break ;		
			case "obstacleOnLeft" :   
				RoomMap.getRoomMap().put(x-1, y, new Box(true, false, false));
				break ;		
			case "obstacleOnUp" :   
				RoomMap.getRoomMap().put(x, y-1, new Box(true, false, false));
				break ;		
			case "obstacleOnDown" :   
				RoomMap.getRoomMap().put(x, y+1, new Box(true, false, false));
				break ;		
			}//switch
		}catch( Exception e) {
			System.out.println("aistruct doMove: ERROR:" +  e.getMessage() );
		}
		String newdir  = initialState.getDirection().toString().toLowerCase()+"Dir";
		int x1         = initialState.getX();
		int y1         = initialState.getY();		
		//update the kb
		System.out.println("aistruct: doMove move=" +  move + " newdir=" + newdir + " x1=" + x1 + " y1="+y1  );
//		qa.solveGoal("replaceRule( curPos(_,_,_), curPos("+ x1 +"," + y1 + ","+ newdir + "))");
// 		if( ! move.equals("a") &&  ! move.equals("d") ) showMap(qa);
// 		else System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	}	
	
	public static void checkIfNextCellCleaned(  ) {
	  String dir = initialState.getDirection().toString();
	  int dimMapx   = RoomMap.getRoomMap().getDimX();
	  int dimMapy   = RoomMap.getRoomMap().getDimY();
	  int x         = initialState.getX();
	  int y         = initialState.getY();
	  boolean clean = false;
		  switch( dir ){
		     case "upDir"     : clean = (y > 0) && ! RoomMap.getRoomMap().isDirty(x,y-1); break;
		     case "downDir"   : clean = (y < dimMapy-1) && ! RoomMap.getRoomMap().isDirty(x,y+1); break;
		     case "leftDir"   : clean = (x > 0) && ! RoomMap.getRoomMap().isDirty(x-1,y); break;
		     case "rigthDir"  : clean = (x < dimMapx-1) && ! RoomMap.getRoomMap().isDirty(x+1,y); break;
		  }
//		  if( clean) qa.addRule( "nextCellIsClean" );
	}
	
	public static void showMap(   ) throws Exception {
		System.out.println( RoomMap.getRoomMap().toString() );
	}
 
	
/*
 * ---------------------------------------------------------	
 */
	
	public static void setGoalInit( ) {   
		goalTest = new Functions();
	}
 
/*
 * ------------------------------------------------
 * TIMER	
 * ------------------------------------------------
 */
private static long timeStart = 0;

	public static void startTimer( ) {
		timeStart = System.currentTimeMillis();
	}
	public static void getDuration( ) {
		int duration = (int) (System.currentTimeMillis() - timeStart);
//		qa.replaceRule("moveWDuration(_)", "moveWDuration("+ duration + ")");		 
	}
	
/*
 * Direction
 */
	public static void rotateDirection(  ) {
		//System.out.println("before rotateDirection: " + initialState.getDirection() );
		initialState = (RobotState) new Functions().result(initialState, new RobotAction(RobotAction.TURNLEFT));
 		initialState = (RobotState) new Functions().result(initialState, new RobotAction(RobotAction.TURNLEFT));
		//System.out.println("after  rotateDirection: " + initialState.getDirection() );
		//update the kb
		int x         = initialState.getX();
		int y         = initialState.getY();
		String newdir = initialState.getDirection().toString().toLowerCase()+"Dir"; 		
//		qa.solveGoal("replaceRule( curPos(_,_,_), curPos("+ x +"," + y + ","+ newdir + "))");
 	}
}
