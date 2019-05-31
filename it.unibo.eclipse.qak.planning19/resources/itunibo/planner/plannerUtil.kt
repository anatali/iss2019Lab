package itunibo.planner

import java.util.ArrayList
import aima.core.agent.Action
import aima.core.search.framework.SearchAgent
import aima.core.search.framework.problem.GoalTest
import aima.core.search.framework.problem.Problem
import aima.core.search.framework.qsearch.GraphSearch
import aima.core.search.uninformed.BreadthFirstSearch
import it.unibo.exploremap.stella.model.Box
import it.unibo.exploremap.stella.model.Functions
import it.unibo.exploremap.stella.model.RobotAction
import it.unibo.exploremap.stella.model.RobotState
import it.unibo.exploremap.stella.model.RoomMap
import it.unibo.exploremap.stella.model.RobotState.Direction

object plannerUtil {
    private var initialState: RobotState? = null
	private var actions: List<Action>?    = null
/*
 * ------------------------------------------------
 * PLANNING
 * ------------------------------------------------
 */
    private var search: BreadthFirstSearch? = null
    lateinit var goalTest: GoalTest
    private var timeStart: Long = 0

    @Throws(Exception::class)
    fun initAI() {
        println("plannerUtil initAI")
        initialState = RobotState(0, 0, RobotState.Direction.DOWN)
        search = BreadthFirstSearch(GraphSearch())
    }

//    @Throws(Exception::class)
//    fun cleanQa() {
//        println("plannerUtil cleanQa")
//        setGoalInit()
//        RoomMap.getRoomMap().setDirty()
//    }

    @Throws(Exception::class)
    fun cell0DirtyForHome() {
        RoomMap.getRoomMap().put(0, 0, Box(false, true, false))
    }

    @Throws(Exception::class)
    fun doPlan(): List<Action>? {
        //var actions: List<Action>?
        val searchAgent: SearchAgent
        //println("plannerUtil doPlan newProblem (A) " );
        val problem = Problem(initialState, Functions(), Functions(), goalTest, Functions())
        //println("plannerUtil doPlan newProblem (A) search " );
        searchAgent = SearchAgent(problem, search!!)
        actions     = searchAgent.actions
        if (actions == null || actions!!.isEmpty()) {
            println("plannerUtil doPlan NO MOVES !!!!!!!!!!!! $actions!!"   )
            if (!RoomMap.getRoomMap().isClean) RoomMap.getRoomMap().setObstacles()
            //actions = ArrayList()
            return null
        } else if (actions!![0].isNoOp) {
            println("plannerUtil doPlan NoOp")
            return null
        }
        println("plannerUtil doPlan actions=$actions")
        return actions
    }
	
    fun executeMoves( ) {
		if( actions == null ) return
        val iter = actions!!.iterator()
        while (iter.hasNext()) {
            plannerUtil.doMove(iter.next().toString())
        }
    }


/*
* ------------------------------------------------
* MAP UPDATE
* ------------------------------------------------
*/

    @Throws(Exception::class)
    fun doMove(move: String) {
        val dir = initialState!!.direction
        val dimMapx = RoomMap.getRoomMap().dimX
        val dimMapy = RoomMap.getRoomMap().dimY
        val x = initialState!!.x 
        val y = initialState!!.y
       //println("plannerUtil: doMove move=$move  dir=$dir x=$x y=$y dimMapX=$dimMapx dimMapY=$dimMapy")
       try {
            when (move) {
                "w" -> {
                    RoomMap.getRoomMap().put(x, y, Box(false, false, false)) //clean the cell
                    initialState = Functions().result(initialState!!, RobotAction(RobotAction.FORWARD)) as RobotState
                    RoomMap.getRoomMap().put(initialState!!.x, initialState!!.y, Box(false, false, true))
                }
                "s" -> {
                    initialState = Functions().result(initialState!!, RobotAction(RobotAction.BACKWARD)) as RobotState
                    RoomMap.getRoomMap().put(initialState!!.x, initialState!!.y, Box(false, false, true))
                }
                "a" -> {
                    initialState = Functions().result(initialState!!, RobotAction(RobotAction.TURNLEFT)) as RobotState
                    RoomMap.getRoomMap().put(initialState!!.x, initialState!!.y, Box(false, false, true))
                }
                "d" -> {
                    initialState = Functions().result(initialState!!, RobotAction(RobotAction.TURNRIGHT)) as RobotState
                    RoomMap.getRoomMap().put(initialState!!.x, initialState!!.y, Box(false, false, true))
                }
                "c"    //forward and  clean
                -> {
                    RoomMap.getRoomMap().put(x, y, Box(false, false, false))
                    initialState = Functions().result(initialState!!, RobotAction(RobotAction.FORWARD)) as RobotState
                    RoomMap.getRoomMap().put(initialState!!.x, initialState!!.y, Box(false, false, true))
                }
                "obstacleOnRight" -> RoomMap.getRoomMap().put(x + 1, y, Box(true, false, false))
                "obstacleOnLeft" -> RoomMap.getRoomMap().put(x - 1, y, Box(true, false, false))
                "obstacleOnUp" -> RoomMap.getRoomMap().put(x, y - 1, Box(true, false, false))
                "obstacleOnDown" -> RoomMap.getRoomMap().put(x, y + 1, Box(true, false, false))
            }//switch
        } catch (e: Exception) {
            println("plannerUtil doMove: ERROR:" + e.message)
        }

        val newdir = initialState!!.direction.toString().toLowerCase() + "Dir"
        val x1     = initialState!!.x
        val y1     = initialState!!.y
        //update the kb
        //println("plannerUtil: doMove move=$move newdir=$newdir x1=$x1 y1=$y1")
    }

    fun checkIfNextCellCleaned() {
        val dir = initialState!!.direction.toString()
        val dimMapx = RoomMap.getRoomMap().dimX
        val dimMapy = RoomMap.getRoomMap().dimY
        val x = initialState!!.x
        val y = initialState!!.y
        var clean = false
        when (dir) {
            "upDir"    -> clean = y > 0 && !RoomMap.getRoomMap().isDirty(x, y - 1)
            "downDir"  -> clean = y < dimMapy - 1 && !RoomMap.getRoomMap().isDirty(x, y + 1)
            "leftDir"  -> clean = x > 0 && !RoomMap.getRoomMap().isDirty(x - 1, y)
            "rigthDir" -> clean = x < dimMapx - 1 && !RoomMap.getRoomMap().isDirty(x + 1, y)
        }
        //		  if( clean) qa.addRule( "nextCellIsClean" );
    }

    @Throws(Exception::class)
    fun showMap() {
        println(RoomMap.getRoomMap().toString())
    }
/*
 * ---------------------------------------------------------
 */
    fun setGoalInit() {
        goalTest = Functions()
    }

	fun setGoal( x: String, y: String) {
		setGoal( Integer.parseInt(x), Integer.parseInt(y))
	}	

    fun setGoal( x: Int, y: Int) {
        try {
            println("setGoal $x,$y")
            RoomMap.getRoomMap().put(x, y, Box(false, true, false))
            goalTest = GoalTest { state  : Any ->
                val robotState = state as RobotState
				(robotState.x == x && robotState.y == y)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun startTimer() {
        timeStart = System.currentTimeMillis()
    }
    fun getDuration() {
        val duration = (System.currentTimeMillis() - timeStart).toInt()
        //		qa.replaceRule("moveWDuration(_)", "moveWDuration("+ duration + ")");
		println("DURATION = $duration")
    }

/*
 * Direction
 */
    fun rotateDirection() {
        //println("before rotateDirection: " + initialState.getDirection() );
        initialState = Functions().result(initialState!!, RobotAction(RobotAction.TURNLEFT)) as RobotState
        initialState = Functions().result(initialState!!, RobotAction(RobotAction.TURNLEFT)) as RobotState
        //println("after  rotateDirection: " + initialState.getDirection() );
        //update the kb
        val x = initialState!!.x
        val y = initialState!!.y
        val newdir = initialState!!.direction.toString().toLowerCase() + "Dir"
    }
}
