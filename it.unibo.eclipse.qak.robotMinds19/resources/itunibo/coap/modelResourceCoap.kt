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

class modelResourceCoap (name : String ) : CoapResource(name) {

	
	companion object {
		lateinit var actor : ActorBasic
		
		fun create( a: ActorBasic, name: String  ){
			actor = a
			val server  = CoapServer(5683);
			server.start();
			server.add( modelResourceCoap( name ) );
			System.out.println("Coap Server started");	
		}
		
	}
	 
	override fun handleGET(exchange: CoapExchange?) {
		actor.solve("model( actuator, robot, state(STATE) )")
		val result = actor.getCurSol("STATE").toString()
		println("handleGET result= $result" )
		exchange!!.respond(ResponseCode.CONTENT, result, MediaTypeRegistry.TEXT_PLAIN)
	}

	 
	override fun handlePUT(exchange: CoapExchange?) {
		try {
			val value = exchange!!.getRequestText()//new String(payload, "UTF-8");
			println("handlePUT value= $value"  )
			
			actor.scope.launch{
				MsgUtil.sendMsg( "modelChange", "modelChange( robot,$value )", actor )
				exchange!!.respond(CHANGED, "DONE $value")
			}
 			
			//this.changed()
			//exchange!!.respond(CHANGED, myVal)
		} catch (e: Exception) {
			exchange!!.respond(BAD_REQUEST, "Invalid String")
		}
	}
}