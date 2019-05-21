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

}