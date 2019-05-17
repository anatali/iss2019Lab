package it.unibo.eclipse.qak.robot

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

class TestVirtualRobotExecutor {
	companion object{
		var startUpDone = false
	}
		
	var model : ActorBasic? = null
	var robot : ActorBasic? = null
	
	@Before
	@Throws(Exception::class)
	fun systemSetUp() {
		//if( ! startUpDone ){
			println(" %%%%%%% TestVirtualRobotExecutor starts ---------------------------------------- ")
	 		GlobalScope.launch{ it.unibo.ctxRobotMvc.main() } //
			delay(4000)
			startUpDone = true;
			getActors()			
		//}
 	}

	@After
	fun terminate() {
		println(" %%%%%%% TestVirtualRobotExecutor terminate ")
	}

	
	protected fun getActors() {		
		println(" %%%%%%% TestVirtualRobotExecutor getActors ")
		model = sysUtil.getActor("resourcemodel")
		robot = sysUtil.getActor("robotmvc")
		//println(" %%%%%%% TestVirtualRobotExecutor model=$model!! + robot=$robot!! ")
 	}


	fun delay( time : Long ){
		Thread.sleep( time )
	}

	fun generateEnvCond( actor : ActorBasic, what : String ){
		actor.scope.launch{
			actor.emit("envCond","envCond( $what )" )
		}		
	}
	fun moveRobot( actor : ActorBasic, move : String ){
		actor.scope.launch{
			MsgUtil.sendMsg("modelChange","modelChange( robot, $move)",model!!)
		}
		delay(100) //give the time to update the model
	}
	

	fun solveCheckGoal( actor : ActorBasic, goal : String ){
		actor.solve( goal  )
		var result =  model!!.resVar
		//println(" %%%%%%%  goal= $goal  result = $result")
		assertTrue("", result == "success" )
	}

	fun stoprobot() {
		println(" %%%%%%% TestVirtualRobotExecutor  stoprobot ===============")
		moveRobot( robot!!, "h")			
		solveCheckGoal( model!!,  "model( actuator, robot, state(stopped) )" )
	}
	fun moveForward(  ) {
		println(" %%%%%%% TestVirtualRobotExecutor  moveForward ===============")
		moveRobot( robot!!, "w")			
		solveCheckGoal( model!!, "model( actuator, robot, state(movingForward) )" )
 	}
	
	fun moveBackward(  ) {
		println(" %%%%%%% TestVirtualRobotExecutor  moveBackward ===============")
		moveRobot( robot!!, "s")			
		solveCheckGoal( model!!, "model( actuator, robot, state(movingBackward) )" )
 	}

	 
	fun rotateLeft() {
		println(" %%%%%%% TestVirtualRobotExecutor  rotateLeft ===============")
		moveRobot( robot!!, "a")			
		solveCheckGoal( model!!, "model( actuator, robot, state(rotateLeft) )" )
	}
	
	 
	fun rotateRight() {
		println(" %%%%%%% TestVirtualRobotExecutor  rotateRight ===============")
		moveRobot( robot!!, "d")			
		solveCheckGoal( model!!, "model( actuator, robot, state(rotateRight) )" )
	}
				
	@Test
	fun moveTest() {
		println(" %%%%%%% TestVirtualRobotExecutor  moveTest ===============")
		rotateRight()
		delay(700)
		rotateLeft()
		delay(500)
		moveForward()
		delay(700)
		moveBackward()
		delay(700)
		stoprobot()
		
		model!!.solve( "model( actuator, robot, state(STATE) )", "STATE"  )
		println( "FINAL ROBOT STATE= ${model!!.resVar}")
 	}
	
	@Test
	fun moveAlarmTest() {
		println(" %%%%%%% TestVirtualRobotExecutor  moveAlarmTest ===============")
		moveForward()
		delay(500)
		generateEnvCond( model!!, "alarm(fire)" )
		delay(200)
 		
		model!!.solve( "model( actuator, robot, state(STATE) )", "STATE"  )
		println( "FINAL ROBOT STATE= ${model!!.resVar}")

				solveCheckGoal( model!!,  "model( actuator, robot, state(stopped) )" )
 	}
	
}