package resources

import it.unibo.radar.common.RadarControl

object radarSupport{
	lateinit var radar : RadarControl 
	
	fun create(){
		radar = RadarControl(null)
	}
	fun sendData( dist: String, theta: String ){
		val d = Integer.parseInt(dist)*5	//Amplify
		radar.update(   "$d",   theta );
	}
}