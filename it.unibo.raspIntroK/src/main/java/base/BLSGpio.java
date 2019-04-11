package base;

import java.io.FileWriter;

public class BLSGpio  {
protected IGpio gpio = new GpioOnSys();
protected FileWriter fwrLed;
 
	public void doJob() throws Exception{
		prepareGpioButton();
		prepareGpioLed();
 		doCmdBlink();
		System.out.println("base.BLSGpio bye bye");
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
			//System.out.println("base.BLSGpio reading ...");
			inps = gpio.readGPio(IGpioConfig.gpioInButton );
			System.out.println("base.BLSGpio -> " + inps);
			if( inps.equals("0") ){
				gpio.writeGPio( fwrLed, IGpio.GPIO_OFF);
 			}
			if( inps.equals("1") ){
				gpio.writeGPio( fwrLed, IGpio.GPIO_ON);
 			}
			// Wait for a while
			Thread.sleep(400);
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
