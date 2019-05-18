package it.unibo.robotRaspOnly

import it.unibo.iot.baseRobot.hlmodel.BasicRobot
import it.unibo.iot.models.commands.baseRobot.BaseRobotSpeed
import it.unibo.iot.models.commands.baseRobot.BaseRobotSpeedValue
import it.unibo.iot.models.commands.baseRobot.BaseRobotStop
import it.unibo.iot.models.commands.baseRobot.BaseRobotForward
import it.unibo.iot.models.commands.baseRobot.BaseRobotBackward
import it.unibo.iot.models.commands.baseRobot.BaseRobotLeft
import it.unibo.iot.models.commands.baseRobot.BaseRobotRight
import it.unibo.iot.models.commands.baseRobot.IBaseRobotCommand
//import it.unibo.iot.sensors.ISensorObserver
//import it.unibo.iot.models.sensorData.ISensorData
//import it.unibo.iot.models.sensorData.SensorData

object nanoSupport {
	
	val SPEED_LOW     = BaseRobotSpeed(BaseRobotSpeedValue.ROBOT_SPEED_LOW)
	val SPEED_MEDIUM  = BaseRobotSpeed(BaseRobotSpeedValue.ROBOT_SPEED_MEDIUM)
	val SPEED_HIGH    = BaseRobotSpeed(BaseRobotSpeedValue.ROBOT_SPEED_HIGH)
	
	val basicRobot    = BasicRobot.getRobot()
	val robot         = basicRobot.getBaseRobot()
 
	fun move( cmd : String ){
		var command : IBaseRobotCommand = BaseRobotStop(SPEED_LOW )
		when( cmd ){
			"msg(w)" -> command = BaseRobotForward( SPEED_HIGH )
			"msg(s)" -> command = BaseRobotBackward(SPEED_HIGH )
			"msg(a)" -> command = BaseRobotLeft(SPEED_MEDIUM )
			"msg(d)" -> command = BaseRobotRight(SPEED_MEDIUM )
			"msg(h)" -> command = BaseRobotStop(SPEED_LOW )
		}
		robot.execute(command)
	}
	
//	fun addObserverToSensors(  observer : ISensorObserver<SensorData> ){ //<T : ISensorData!>
//		for ( sensor in basicRobot.getSensors() ) {  
//			println( "adding observer to sensor: "  + sensor.getDefStringRep()  );
// 			//sensor.addObserver (observer  );
//		}
//	}		
}