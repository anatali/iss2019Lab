package it.unibo.eclipse.qak.robots19

import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.After
import org.junit.Before
import org.junit.Test
import alice.tuprolog.SolveInfo
import it.unibo.kactor.sysUtil
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay

class TestRobotmindnomvc {
	companion object{
		var startUpDone = false
	}
		
 	var robot : ActorBasic? = null
	
	@Before
	@Throws(Exception::class)
	fun systemSetUp() {
		//if( ! startUpDone ){
 		/*
            IT IS NOT NECESSARY TO START the basic robot.
 			In any case the basic robot must be lanched from a another isntance of JVM
        */ 
	 		GlobalScope.launch{
 			    println(" %%%%%%% TestRobotmindnomvc starts robot mind ")
				it.unibo.ctxRobotMindNoMv.main()
 			}
			delay(2000)
			startUpDone = true;
			getActors()			
		    println(" %%%%%%% TestRobotmindnomvc starts its job %%%%%% ")
		//}
 	}
	protected fun getActors() {		
		//println(" %%%%%%% TestRobotmindnomvc getActors ")
 		robot = sysUtil.getActor("robotmindnomvc")
		println(" %%%%%%% TestRobotmindnomvc getActors robot=${robot}")
 	}

	@After
	fun terminate() {
		println(" %%%%%%% TestRobotmindnomvc terminate ")
	}
 
	@Test
	fun moveTest() {
		println(" %%%%%%% TestRobotmindnomvc  moveTest ")
 		rotateRight(500)
 		rotateLeft(500)
 		moveForward(700)	//no obstacle assumed
 		moveBackward(700)	//no obstacle assumed
 		stoprobot(100)
		moveForwardWithObstaclePlanned(2000)
		
		robot!!.solve( "model( actuator, robot, state(STATE) )", "STATE"  )
		println( "FINAL ROBOT STATE= ${robot!!.resVar}")
 	}

	fun stoprobot(time:Long) {
		println(" %%%%%%% TestRobotmindnomvc  stoprobot %%%")
		moveRobot( robot!!, "h", time)			
		solveCheckGoal( robot!!,  "model( actuator, robot, state(stopped) )" )
	}
	fun moveForward( time:Long ) {
		println(" %%%%%%% TestRobotmindnomvc  moveForward %%%")
		moveRobot( robot!!, "w", time)			
		solveCheckGoal( robot!!, "model( actuator, robot, state(movingForward) )" )
 	}
	fun moveForwardWithObstaclePlanned( time:Long ) {
		println(" %%%%%%% TestRobotmindnomvc  moveForwardWithObstaclePlanned %%%")
	 		GlobalScope.launch{
				delay( time / 2 )
 			    println(" %%%%%%% TestRobotmindnomvc SIMULATES OBSTACLE")
				robot!!.emit("sonarRobot", "sonar(8)")
 			}
		moveRobot( robot!!, "w", time)			
		solveCheckGoal( robot!!, "model( actuator, robot, state(stopped) )" )
	}
	
	fun moveBackward( time:Long ) {
		println(" %%%%%%% TestRobotmindnomvc  moveBackward %%%")
		moveRobot( robot!!, "s", time)			
		solveCheckGoal( robot!!, "model( actuator, robot, state(movingBackward) )" )
 	}
	 
	fun rotateLeft( time:Long ) {
		println(" %%%%%%% TestRobotmindnomvc  rotateLeft %%%")
		moveRobot( robot!!, "a", time)			
		solveCheckGoal( robot!!, "model( actuator, robot, state(rotateLeft) )" )
	}
	 
	fun rotateRight( time:Long ) {
		println(" %%%%%%% TestRobotmindnomvc  rotateRight %%%")
		moveRobot( robot!!, "d", time)			
		solveCheckGoal( robot!!, "model( actuator, robot, state(rotateRight) )" )
	}	

//----------------------------------------
	fun moveAlarmTest() {
		println(" %%%%%%% TestRobotmindnomvc  moveAlarmTest %%%")
		moveForward(500)
 		generateEnvCond( robot!!, "alarm(fire)" )
		delay(200)
 		
//		robot!!.solve( "robot( actuator, robot, state(STATE) )", "STATE"  )
//		println( "FINAL ROBOT STATE= ${robot!!.resVar}")
//
//				solveCheckGoal( robot!!,  "robot( actuator, robot, state(stopped) )" )
 	}

	
	fun delay( time : Long ){
		Thread.sleep( time )
	}

	fun generateEnvCond( actor : ActorBasic, what : String ){
		actor.scope.launch{
			actor.emit("envCond","envCond( $what )" )
		}		
	}
	fun moveRobot( actor : ActorBasic, move : String, time : Long ){
		actor.scope.launch{
			//println("emit robotCmd($move)")
			robot!!.emit("userCmd","userCmd($move)") //simulate the effect of an user command
			//MsgUtil.sendMsg("robotCmd","robotCmd($move)",robot!!)
 		}
		delay(time) //give time to do the move
  	}
	

	fun solveCheckGoal( actor : ActorBasic, goal : String ){
		actor.solve( goal  )
		var result =  robot!!.resVar
		println(" %%%%%%%  goal= $goal  result = $result")
		assertTrue("", result == "success" )
	}


				
	
	 
	
}