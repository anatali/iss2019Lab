package itunibo.coap;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
 
public class MainCoapBasic {
private static CoapServer server;
private static CoapClient coapClient;

//private static Request request = new Request(Code.GET);
private static AsynchListener asynchListener = new AsynchListener();
	
	public static void createServer(int port) {	//port=5683 default
		server   = new CoapServer(port);
		server.start();
		System.out.println("Server started");
	}
	
	public static void createClient(int port, String resourceName) {
 		coapClient= new CoapClient("coap://localhost:"+port+"/"+resourceName);
 		//new  CoapClient("coap://localhost:8010/robot" );
		System.out.println("Client started");
	}
	
	public static void addResource(String name) {
		server.add( new ResourceCoap(name) );
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

	public static void asynchGet() {
 		coapClient.get( asynchListener );
	}
	
	public static void put(String v) {
		CoapResponse coapResp = coapClient.put(v, MediaTypeRegistry.TEXT_PLAIN);
		//The "CoapResponse" message contains the response.
 		System.out.println("%%% ANSWER put " );
		System.out.println(Utils.prettyPrint(coapResp));
	}
	
 	
	public static void main(String[] args) throws Exception {
		String resourceName="robot";
		int port = 5683; //8010;
		MainCoapBasic.createServer(port);
		addResource(resourceName);
		createClient(port,resourceName);
		synchGet();
		Thread.sleep(500);
		put("false");
		Thread.sleep(1000);
		System.out.println("NEW VALUE:");
		asynchGet();
		
	}	
}
