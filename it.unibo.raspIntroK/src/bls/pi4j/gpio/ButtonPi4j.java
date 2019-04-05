package it.unibo.bls.pi4j.gpio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class ButtonPi4j {
	protected   GpioController gpioControl;
	protected   int dt = 500;
	protected   GpioPinDigitalInput pinButton;
	
	public ButtonPi4j(){
		try {
			configure();
 			terminate();
		} catch (Exception e) {
 			e.printStackTrace();
		}
	}
	
	protected void configure() throws Exception{
		 System.out.println("=========== CONFIGURE =======================");	
	     gpioControl = GpioFactory.getInstance();
	     // provision gpio pin as an output pin and turn on
	     pinButton = gpioControl.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_DOWN);

	     pinButton.addListener(new GpioPinListenerDigital() {
             @Override
             public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                  if(event.getState().isHigh()){
                	 System.out.println("BUTTON LISTENER : button HIGH ");	
                 }else{
                   	 System.out.println("BUTTON LISTENER : button LOW ");	
                 }
             }
         });
	     
	     
 	     System.out.println("configure -->  ");		
	     System.out.print(">>>");System.in.read();
	}
	
	
	
 	protected void terminate(){
		System.out.println("=========== TERMINATE   ===================");	
        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        gpioControl.shutdown();		
	}
	
	public static void main(String[] args) throws InterruptedException {
		new ButtonPi4j(); 
	}
}
