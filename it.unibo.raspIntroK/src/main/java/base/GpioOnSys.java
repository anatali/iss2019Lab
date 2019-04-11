package base;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

public class GpioOnSys implements IGpio{
public static final GpioController gpioPi4j = GpioFactory.getInstance();
	
	public void prepareGpio(String gpioChannel) throws IOException{
		System.out.println("	GpioOnSys unexport and export controls");
		// Open file handles to GPIO port unexport and export controls
		FileWriter unexportFile = new FileWriter("/sys/class/gpio/unexport");
		FileWriter exportFile = new FileWriter("/sys/class/gpio/export");
		// Reset the port
		System.out.println("	GpioOnSys reset the port for " + gpioChannel);
		File exportFileCheck = new File("/sys/class/gpio/gpio"+ gpioChannel);
		if (exportFileCheck.exists()) {
			unexportFile.write(gpioChannel);
			unexportFile.flush();
		}
		// Set the port for use
		System.out.println("	GpioOnSys set the port for use for " + gpioChannel);
		exportFile.write(gpioChannel);
		exportFile.flush();		
	}
	
	public FileWriter openOutputDirection( String gpioChannel ) throws IOException{
 		FileWriter directionFile = new FileWriter("/sys/class/gpio/gpio"+ gpioChannel + "/direction");
 		// Set port for output
		directionFile.write(GPIO_OUT);
		directionFile.flush();
		/*** Send commands to GPIO port ***/
 		System.out.println("	GpioOnSys open file handle to issue commands to GPIO port " + gpioChannel);
		FileWriter commandFile = new FileWriter("/sys/class/gpio/gpio"+ gpioChannel + "/value");
		return commandFile;
	}

	public void openInputDirection( String gpioChannel ) throws IOException{
 		FileWriter directionFile = new FileWriter("/sys/class/gpio/gpio"+ gpioChannel + "/direction");
 		// Set port for output
		directionFile.write(GPIO_IN);
		directionFile.flush();
		/*** Send commands to GPIO port ***/
 		System.out.println("	GpioOnSys open file handle to input from GPIO port " + gpioChannel);
 	}
	
	public void writeGPio(FileWriter commandFile, String value) throws Exception{
		commandFile.write(value);
		commandFile.flush();		
	}
	public void writeGPio(String gpioChannel, String value) throws Exception{
		FileWriter commandFile = new FileWriter("/sys/class/gpio/gpio"+ gpioChannel + "/value");
		commandFile.write(value);
		commandFile.close();		
	}
	public String readGPio( String gpioChannel ) throws Exception{
		FileReader fr = new FileReader("/sys/class/gpio/gpio"+ gpioChannel + "/value");
 		int v = 0;
		int res = 0;
 		res = fr.read();
 		v = res;
		while( v != 10 ){
 			v = fr.read();
		}
// 		fr.close();
 		return ""+(char)res;
 	}
	@Override
	public GpioController getGpioPi4j() {
 		return gpioPi4j;
	}	
 }

