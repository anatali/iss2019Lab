package base;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public class GpioOnPi4j implements IGpioPi4j{
public static final GpioController controller = GpioFactory.getInstance();

	public static Pin getPin(int pinNum) {
  		switch(pinNum){
 		case 4 : return RaspiPin.GPIO_07; 
 		case 17 : return RaspiPin.GPIO_00; 
		case 18 : return RaspiPin.GPIO_01; 
		case 21 : return RaspiPin.GPIO_02; 
		case 22 : return RaspiPin.GPIO_03; 
		case 23 : return RaspiPin.GPIO_04; 
		case 24 : return RaspiPin.GPIO_05; 
		case 25 : return RaspiPin.GPIO_06; 		 		
		case 27 : return RaspiPin.GPIO_02; 
 		}
 		return RaspiPin.GPIO_00;
	}
	@Override
	public GpioController getGpioPi4j() {
 		return controller;
	}
	@Override
	public GpioPinDigitalOutput setPinOut( int pinNum, String name, boolean state){
		return null;
	}
  }

