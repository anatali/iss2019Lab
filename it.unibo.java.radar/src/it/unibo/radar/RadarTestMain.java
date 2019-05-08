package it.unibo.radar;

//import it.unibo.baseEnv.basicFrame.EnvFrame;
//import it.unibo.is.interfaces.IBasicEnvAwt;
import it.unibo.is.interfaces.IOutputEnvView;

import java.awt.Color;

public class RadarTestMain {
	
	public static void delay( int dt ) {
		try {
			Thread.sleep(dt);
		} catch (InterruptedException e) {
 			e.printStackTrace();
		}
	}

	public static void init( IOutputEnvView outEnvView ) {
		it.unibo.radar.common.radarSupport.setUpRadarGui();
		//delay(2000); //give time to create the gui
		System.out.println("RadarTest STARTED ");
		it.unibo.radar.common.radarSupport.update("40","90");
	}
	
	public static void main(String[] args){
		//IBasicEnvAwt env = new EnvFrame("RadarTes", Color.cyan,Color.black);
		//init( env.getOutputEnvView() );
		init( null );
		//test();
  	}

}
