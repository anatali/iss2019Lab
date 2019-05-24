package itunibo.coap;

import static org.eclipse.californium.core.coap.CoAP.ResponseCode.BAD_REQUEST;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.CHANGED;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class ResourceCoap extends CoapResource{

	private String myVal = "true";
	
	public ResourceCoap(String name) {
		super(name);
		setObservable(true);
//		getAttributes().setObservable();
		
 	}

 
	@Override
	public void handleGET(CoapExchange exchange) {
        System.out.println("handleGET myVal= "+   myVal );
		exchange.respond(ResponseCode.CONTENT, myVal, MediaTypeRegistry.TEXT_PLAIN);
	}
 
    @Override
    public  void handlePUT(CoapExchange exchange) {
         try {
        	String value = exchange.getRequestText();//new String(payload, "UTF-8"); 
            System.out.println("handlePUT value= "+ value + " from " + myVal );
        	myVal = value;
        	this.changed();
            exchange.respond(CHANGED,  myVal);
        } catch (Exception e) {
             exchange.respond(BAD_REQUEST, "Invalid String");
        }
    }

}
