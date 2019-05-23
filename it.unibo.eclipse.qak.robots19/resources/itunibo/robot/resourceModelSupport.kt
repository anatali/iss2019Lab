package itunibo.robot

import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.launch

object resourceModelSupport{
	
	fun updateModel( actor: ActorBasic, content: String ){
 			actor.solve(  "action( robot, move($content) )" ) //change the robot state model
			actor.solve( "model( A, R, STATE )" )
			val RobotState = actor.getCurSol("STATE")
			println("			resourceModelSupport updateModel RobotState=$RobotState")
			actor.scope.launch{
 				actor.emit( "modelContent" , "content( robot( $RobotState ) )" )
 			}	
	}	
}

