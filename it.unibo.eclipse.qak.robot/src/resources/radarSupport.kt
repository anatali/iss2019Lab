package resources

import it.unibo.radar.common.radarSupport.*

object radarSupport{
		 fun activate(){
			println("ACTIVATE THE RADAR")
			setUpRadarGui()
 		}		
		fun spot( distance : Int, angle: Int ){
			update(distance.toString(),angle.toString())
		}
		fun spot( distance : String, angle: String ){
			update(distance,angle)
		}

	/*
	lateinit var radar : RadarControl 
	
	fun create(){
		it.unibo.radar.common.radarSupport.setUpRadarGui()
		radar = RadarControl(null)
	}
	fun sendData( dist: String, theta: String ){
		val d = Integer.parseInt(dist)*5	//Amplify
		radar.update(   "$d",   theta );
	}
 */
}