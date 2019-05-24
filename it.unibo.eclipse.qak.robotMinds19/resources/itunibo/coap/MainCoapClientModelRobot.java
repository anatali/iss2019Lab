package itunibo.coap;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
 
public class MainCoapClientModelRobot {
private static CoapClient coapClient;

	
	public static void createClient(int port, String resourceName) {
 		coapClient= new CoapClient("coap://localhost:"+port+"/"+resourceName);
 		//new  CoapClient("coap://localhost:8010/robot" );
		System.out.println("Client started");
	}
	
 	
	public static void synchGet() {
 		System.out.println("%%% synchGet " );
		//Synchronously send the GET message (blocking call)
//		CoapResponse coapResp = coapClient.advanced(request);
		CoapResponse coapResp = coapClient.get();
		//The "CoapResponse" message contains the response. 
 		System.out.println("%%% ANSWER get " );
		System.out.println(Utils.prettyPrint(coapResp));
	}


	
	public static void put(String v) {
		CoapResponse coapResp = coapClient.put(v, MediaTypeRegistry.TEXT_PLAIN);
		//The "CoapResponse" message contains the response.
 		System.out.println("%%% ANSWER put " );
		System.out.println(Utils.prettyPrint(coapResp));
	}
	
 	
	public static void main(String[] args) throws Exception {
		String resourceName="resourcemodel";
 		createClient(5683,resourceName);
		synchGet();
		put("w");
		Thread.sleep(1000);
	}	
}
