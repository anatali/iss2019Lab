package resources

class radarSupport {
	companion object{
		fun activate(){
			println("ACTIVATE THE RADAR")
			it.unibo.radar.common.radarSupport.setUpRadarGui()
 		}
		
		fun showSpot( distance : Int, angle: Int ){
			it.unibo.radar.common.radarSupport.update(distance.toString(),angle.toString())
		}
		fun showSpot( distance : String, angle: String ){
			it.unibo.radar.common.radarSupport.update(distance,angle)
		}
	}
}