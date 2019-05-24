package itunibo.robot

import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.launch

object resourceModelSupport{
	 
	fun updateRobotModel( actor: ActorBasic, content: String ){
 			actor.solve(  "action(robot, move($content) )" ) //change the robot state model
			actor.solve(  "model( A, robot, STATE )" )
			val RobotState = actor.getCurSol("STATE")
			//println("			resourceModelSupport updateModel RobotState=$RobotState")
			actor.scope.launch{
 				actor.emit( "modelContent" , "content( robot( $RobotState ) )" )
 			}	
	}	
	fun updateSonarRobotModel( actor: ActorBasic, content: String ){
 			actor.solve( "action( sonarRobot,  $content )" ) //change the robot state model
			actor.solve( "model( A, sonarRobot, STATE )" )
			val SonarState = actor.getCurSol("STATE")
			//println("			resourceModelSupport updateSonarRobotModel SonarState=$SonarState")
			actor.scope.launch{
 				actor.emit( "modelContent" , "content( sonarRobot( $SonarState ) )" )
 			}	
	}	
 	
}

