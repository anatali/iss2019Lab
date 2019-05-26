package itunibo.coap
import org.eclipse.californium.core.coap.CoAP.ResponseCode.BAD_REQUEST
import org.eclipse.californium.core.coap.CoAP.ResponseCode.CHANGED
import org.eclipse.californium.core.CoapResource
import org.eclipse.californium.core.coap.CoAP.ResponseCode
import org.eclipse.californium.core.coap.MediaTypeRegistry
import org.eclipse.californium.core.server.resources.CoapExchange
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import org.eclipse.californium.core.CoapServer
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.GlobalScope
import org.eclipse.californium.core.coap.CoAP.Type

class modelResourceCoap (name : String ) : CoapResource(name) {
	
	companion object {
		lateinit var actor : ActorBasic
		var result = "unknown"
		
		fun create( a: ActorBasic, name: String  ){
			actor = a
			val server  = CoapServer(5683);		//COAP SERVER
			server.add( modelResourceCoap( name ) );
			server.start();
			println("--------------------------------------------------")
			println("Coap Server started");	
			println("--------------------------------------------------")
		}		
	}
	
	init{ 
		println("--------------------------------------------------")
		println("modelResourceCoap init")
		println("--------------------------------------------------")
		setObservable(true) 				// enable observing	!!!!!!!!!!!!!!
		setObserveType(Type.CON)			// configure the notification type to CONs
		//getAttributes().setObservable();	// mark observable in the Link-Format			
	}
	
	fun updateState(){
		val curState = result		
		actor.solve("model( actuator, robot, state(STATE) )")
		result = actor.getCurSol("STATE").toString()
		//println("%%%%%%%%%%%%%%%% updateState from $curState to $result" )
		changed()	// notify all CoAp observers		
        	/*
        	 * Notifies all CoAP clients that have established an observe relation with
        	 * this resource that the state has changed by reprocessing their original
        	 * request that has established the relation. The notification is done by
        	 * the executor of this resource or on the executor of its parent or
        	 * transitively ancestor. If no ancestor defines its own executor, the
        	 * thread that has called this method performs the notification.
        	 */
	}
	 
	override fun handleGET(exchange: CoapExchange?) {
 		//println("%%%%%%%%%%%%%%%% handleGET  result=$result  "  )			
		exchange!!.respond(ResponseCode.CONTENT, result, MediaTypeRegistry.TEXT_PLAIN)
	}

	override fun handlePOST(exchange: CoapExchange?) {
 		//println("%%%%%%%%%%%%%%%% handlePOST  "  )			
	}
	override fun handlePUT(exchange: CoapExchange?) {
		try {
			val value = exchange!!.getRequestText()//new String(payload, "UTF-8");
			//println("%%%%%%%%%%%%%%%% handlePUT value= $value"  )
			//itunibo.robot.resourceModelSupport.updateRobotModel( actor, value )//HAREMFUL SHERTCUT		
			
			val curState = result		
			GlobalScope.launch{
				MsgUtil.sendMsg( "modelChange", "modelChange( robot,$value )", actor )
				delay(100)  //give the time to change the model
				updateState()
 				exchange.respond(CHANGED, "handlePUT FROM $curState to $result")
			}			
 		} catch (e: Exception) {
			exchange!!.respond(BAD_REQUEST, "Invalid String")
		}
	}
}