package itunibo.robotRaspOnly

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.delay

object gy521Support {
	lateinit var reader : BufferedReader
	
	//g++  rotaccel.c -l wiringPi -o  rotaccel
	fun create( actor : ActorBasic, todo : String="" ){
		//println("gy521Support CREATING")
		//val p  = Runtime.getRuntime().exec("sudo python complementaryFiliter.py")
		val p  = Runtime.getRuntime().exec("sudo ./rotaccel")
		reader = BufferedReader(  InputStreamReader(p.getInputStream() ))
		startRead( actor )
	}
	
	fun startRead( actor: ActorBasic  ){
		GlobalScope.launch{
			println("gy521Support startRead reader=$reader")
			while( true ){
				var data = reader.readLine()
				println("gy521Support  $data"   )
//				if( data != null ){
//	 				val m1 = "sonar( $data )"
//					//println("sonarHCSR04Support m1 = $m1"   )
//					actor.emit("sonarRobot",m1 )
//				}
//				delay( 250 )
			}
		}
	} 
}