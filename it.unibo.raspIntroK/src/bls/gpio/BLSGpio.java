package it.unibo.bls.gpio;
import it.unibo.gpio.base.GpioOnSys;
import it.unibo.gpio.base.IGpio;
import it.unibo.gpio.base.IGpioConfig;
import java.io.FileWriter;

public class BLSGpio  {
protected IGpio gpio = new GpioOnSys();
protected FileWriter fwrLed;
 
	public void doJob() throws Exception{
		prepareGpioButton();
		prepareGpioLed();
 		doCmdBlink();
		System.out.println("BLSGpio bye bye");
	}	
 	protected void prepareGpioButton() throws Exception{
		gpio.prepareGpio(IGpioConfig.gpioInButton);
		gpio.openInputDirection(IGpioConfig.gpioInButton);		
	}
	protected void prepareGpioLed() throws Exception{
		gpio.prepareGpio(IGpioConfig.gpioOutLed);
		fwrLed = gpio.openOutputDirection(IGpioConfig.gpioOutLed);		
	}	
	protected void doCmdBlink( ) throws Exception{
		String inps = "";
 		for( int i = 1; i<=15; i++) {
			//System.out.println("BLSGpio reading ...");
			inps = gpio.readGPio(IGpioConfig.gpioInButton );
			System.out.println("BLSGpio -> " + inps);
			if( inps.equals("0") ){
				gpio.writeGPio( fwrLed, IGpio.GPIO_OFF);
 			}
			if( inps.equals("1") ){
				gpio.writeGPio( fwrLed, IGpio.GPIO_ON);
 			}
			// Wait for a while
			java.lang.Thread.sleep(400);
 		}
		gpio.writeGPio( fwrLed, IGpio.GPIO_OFF);
 	}	
	public static void main(String[] args) {
		try {
			BLSGpio sys = new BLSGpio();
			sys.doJob();
 		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
