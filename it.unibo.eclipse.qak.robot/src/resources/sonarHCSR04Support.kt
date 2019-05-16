package resources
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.delay

object sonarHCSR04Support {
	lateinit var reader : BufferedReader
	
	fun init( actor : ActorBasic, todo : String ){
		val p = Runtime.getRuntime().exec("sudo ./SonarAlone")
		reader = BufferedReader(  InputStreamReader(p.getInputStream() ))
		startRead( actor )
	}
	
	fun startRead( actor: ActorBasic  ){
		GlobalScope.launch{
			while( true ){
				var data = reader.readLine()
				println("sonarHCSR04Support data = $data"   )
				if( data != null ){
	 				val m1 = "sonar( $data )"
					//println("sonarHCSR04Support m1 = $m1"   )
					actor.emit("sonarRobot",m1 )
				}
				delay( 250 )
			}
		}
	}
}