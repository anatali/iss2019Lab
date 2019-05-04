package devices;


import devices.arduino.ButtonProxyArduino;
import devices.arduino.LedProxyArduino;
import devices.gui.ButtonAsGui;
import devices.gui.LedAsGui;
import devices.interfaces.IButtonObservable;
import devices.interfaces.ILed;
import devices.mock.ButtonMock;
import devices.mock.LedMock;


public class DeviceFactory {

    public ILed createLed(LedType devType )   {
        switch( devType ) {
            case LedMockObj:   return new LedMock();
            case LedGuiObj:    return LedAsGui.createLed();
            case LedOnArduino: return new LedProxyArduino(
                    DeviceConfig.serialPortNum, DeviceConfig.serialBaudrate );
            default: return null;
        }
    }
    public IButtonObservable createButton(ButtonType devType )   {
        switch( devType ) {
            case ButtonMockObj: return new ButtonMock();
            case ButtonGuiObj: return  ButtonAsGui.createButton("CLICKME");
            case ButtonOnArduino: return new ButtonProxyArduino(
                    DeviceConfig.serialPortNum, DeviceConfig.serialBaudrate );
            default: return null;
        }
    }

}
