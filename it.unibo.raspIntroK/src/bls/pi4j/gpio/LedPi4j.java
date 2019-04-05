package it.unibo.bls.pi4j.gpio;

import java.util.concurrent.Future;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class LedPi4j {
	protected   GpioController gpioControl;
	protected   int dt = 500;
	protected   GpioPinDigitalOutput ledPin;
	
	public LedPi4j(){
		try {
			configure();
			blink(5);
			blinkBuiltIn();
			usePulse();
			useToggle();
			terminate();
		} catch (Exception e) {
 			e.printStackTrace();
		}
	}
	
	protected void configure() throws Exception{
		 System.out.println("=========== CONFIGURE =======================");	
	     gpioControl = GpioFactory.getInstance();
	     // provision gpio pin as an output pin and turn on
	     ledPin = gpioControl.provisionDigitalOutputPin(RaspiPin.GPIO_06, "MyLED", PinState.HIGH);
	     // set shutdown state for this pin
	     ledPin.setShutdownOptions(true, PinState.LOW);
	     System.out.println("configure --> GPIO state should be: ON");		
//	     Thread.sleep(2000);
	     System.out.print(">>>");System.in.read();
	}
	
	protected void blink(int n) throws Exception{
		System.out.println("=========== BLINK    =======================");	
		for(int i=1; i<=n;i++){
			ledPin.low();
			System.out.println("blink --> " + i + " state:" + ledPin.getState());	
			Thread.sleep(dt);
			ledPin.high();
			System.out.println("blink --> " + i + " state:" + ledPin.getState());	
			Thread.sleep(dt);
		}
		ledPin.low();
	    System.out.print(">>>");System.in.read();
	}
	protected void blinkBuiltIn() throws Exception{
		System.out.println("=========== BLINK BUILT IN  ====================");	
		int delay = 3000;
		Future<?> future = ledPin.blink(500,delay);
		while( ! future.isDone() ){
			System.out.println("isDone: "+future.isDone() + " delay= " + delay);
			Thread.sleep(600);
		}
		System.out.println("isDone: "+future.isDone() + " delay= " + delay);
	    System.out.print(">>>");System.in.read();
	}
	
	protected void useToggle() throws Exception{
		System.out.println("=========== TOGGLE   =======================");	
        // invert	current	state
		ledPin.low();
        ledPin.toggle();
        System.out.println("useToggle --> GPIO state should be: ON");		
//        Thread.sleep(2000);
	     System.out.print(">>>");System.in.read();
	}
	
	protected void usePulse() throws Exception{
		System.out.println("=========== PULSE GOING ON (2000)  =======================");	
        ledPin.pulse(2000, false); // set second argument to 'true' use a blocking call
	    System.out.print(">>>");System.in.read();
		System.out.println("=========== PULSE WAITING (3000) =======================");	
        ledPin.pulse(3000, true); // set second argument to 'true' use a blocking call
        System.out.print(">>>");System.in.read();
	}
	protected void terminate(){
		System.out.println("=========== TERMINATE   ===================");	
        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        gpioControl.shutdown();		
	}
	
	public static void main(String[] args) throws InterruptedException {
		new LedPi4j(); 
	}
}
