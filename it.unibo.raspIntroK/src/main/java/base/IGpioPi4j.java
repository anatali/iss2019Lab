package base;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
 

public interface IGpioPi4j {
	public  final String GPIO_OUT = "out";
	public  final String GPIO_IN = "in";
	public  final String GPIO_ON = "1";
	public  final String GPIO_OFF = "0";
 	
	
 	
	public GpioController getGpioPi4j();
	public GpioPinDigitalOutput setPinOut(int pinNum, String name, boolean state);

}
