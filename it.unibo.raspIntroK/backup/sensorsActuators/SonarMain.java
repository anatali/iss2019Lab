package sensorsActuators;
import java.io.BufferedReader;
import java.io.InputStreamReader;

 
/*
 * HC-SR04  UltraSonic Distance Measure Module Range Sensor
 */
public class SonarMain  {
	protected BufferedReader reader;
	protected int distance;

	public SonarMain() {
		doJob();
	}
 
 	protected void doJob()  {
		try{
			System.out.println(" STARTING ... ");
			Process p = Runtime.getRuntime().exec("./SonarAlone");
	 		reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	 		while(true){
	 			String data = reader.readLine();
	 			System.out.println("data = " + data );
 	 		}
 		}catch(Exception e){
 			System.out.println(" ERROR " + e.getMessage() );
		}
 	}
		
  	
	public static void main(String[] args) throws Exception {
 		new SonarMain( );
	}	
}
