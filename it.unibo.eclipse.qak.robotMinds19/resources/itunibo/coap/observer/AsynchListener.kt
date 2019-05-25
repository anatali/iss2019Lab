package itunibo.coap.observer

import org.eclipse.californium.core.CoapHandler
import org.eclipse.californium.core.CoapResponse
import itunibo.outgui.outguiSupport

object AsynchListener : CoapHandler {
 
	val outDev = outguiSupport.create("Resource Coap OBSERVER")
	
	override fun onLoad(response: CoapResponse?) {
		val content = response!!.getResponseText()
		outguiSupport.output("AsynchListener onLoad: $content"  )
	}

	 
	override fun onError() {
		outguiSupport.output("AsynchListener Error")
	}
}