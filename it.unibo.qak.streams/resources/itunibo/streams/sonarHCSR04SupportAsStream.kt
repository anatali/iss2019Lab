package itunibo.streams

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.delay
import it.unibo.kactor.MsgUtil

object sonarHCSR04SupportAsStream {
	lateinit var reader : BufferedReader
	
	//g++  SonarAlone.c -l wiringPi -o  SonarAlone
	fun create( actor : ActorBasic, consumer:  ActorBasic ){
		println("sonarHCSR04SupportAsStream CREATING")
		actor.subscribe( consumer  )		//first part of the pipe
		val p = Runtime.getRuntime().exec("sudo ./SonarAlone")
		reader = BufferedReader(  InputStreamReader(p.getInputStream() ))
		startRead( actor )
	}
	
	fun startRead( actor: ActorBasic  ){
		GlobalScope.launch{
			while( true ){
				var data = reader.readLine()
				//println("sonarHCSR04SupportAsStream data = $data"   )
				if( data != null ){
	 				val m1 = "sonar( $data )"
					//println("sonarHCSR04Support m1 = $m1"   )
					//actor.emit("sonarRobot",m1 )			//AT MODEL LEVEL
					val event = MsgUtil.buildEvent(actor.name,"sonarRobot", m1)
					actor.emitLocalStreamEvent(event)		//AT STREAM LEVEL
				}
				delay( 50 )
			}
		}
	}
}