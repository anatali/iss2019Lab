package itunibo.robot

import it.unibo.kactor.ActorBasicFsm
import it.unibo.kactor.MsgUtil
 
class sonaractorfilter (name : String, val owner : ActorBasicFsm ,
		var LimitDistance : Int = 12, var LastDistance : Int = 0,
		var minDistance  : Int = 2, var maxDistance  : Int = 50,
		var maxDelta   : Int   = 1, var amplif  : Int  = 6 //radar does D/3
) : ApplActorDataStream(name){

		//USING A CONFIGURATION FILE
		init{ 
		this.solve( "consult(\"sonarDataConfig.pl\")" )		 
    	solve("minDistance(D)")
	    minDistance = Integer.parseInt( getCurSol("D").toString() )
    	solve("maxDistance(D)")
	    maxDistance = Integer.parseInt( getCurSol("D").toString() )
    	solve("maxDelta(D)")
	    maxDelta = Integer.parseInt( getCurSol("D").toString() )
    	solve("amplif(D)")
	    amplif = Integer.parseInt(  getCurSol("D").toString() )
	    println("LimitDistance = $LimitDistance , maxDelta=$maxDelta, amplif =$amplif")	
	}
	
	override protected suspend fun elabData(data : String ){
		val Distance = Integer.parseInt( data )
//		val delta    = Math.abs( Distance - LastDistance )
		println("   $name |  elabSonarData Distance = $Distance ")
 		if( Distance > minDistance && Distance < maxDistance ){ //&& delta >= maxDelta 
			//virtual robot IMPACTS => Distance always = 5
			LastDistance = Distance
			val m1 = MsgUtil.buildEvent(name, "sonarData", "sonarData($data)")
			//println("   ${name} |  using ${owner.name}  emit m1= $m1")
 			 emitLocalStreamEvent( m1 )  //PROPAGATE to the pipe
  		}else{
			//println("   $name |  DISCARDS $Distance ")
		}				
	}

}