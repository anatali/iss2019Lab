package base;
import java.io.FileWriter;
import com.pi4j.io.gpio.GpioController;

public interface IGpio {
	public  final String GPIO_OUT = "out";
	public  final String GPIO_IN  = "in";
	public  final String GPIO_ON  = "1";
	public  final String GPIO_OFF = "0";
	
	public void prepareGpio(String gpioChannel) throws  Exception;
	public FileWriter openOutputDirection(String gpioChannel) throws Exception;
	public void openInputDirection(String gpioChannel) throws Exception;
	public void writeGPio(FileWriter commandFile, String value) throws Exception;
	public void writeGPio(String gpioChannel, String value) throws Exception;
	public String readGPio(String gpioChannel) throws Exception;
	
	public GpioController getGpioPi4j();

}
