package it.unibo.bls.devices;

import it.unibo.bls.devices.arduino.ButtonProxyArduino;
import it.unibo.bls.devices.arduino.LedProxyArduino;
import it.unibo.bls.devices.gui.ButtonAsGui;
import it.unibo.bls.devices.gui.LedAsGui;
import it.unibo.bls.devices.mock.ButtonMock;
import it.unibo.bls.devices.mock.LedMock;
import it.unibo.bls.interfaces.IButtonObservable;
import it.unibo.bls.interfaces.ILed;
import jssc.SerialPort;

public class DeviceFactory {

    public ILed createLed( LedType  devType )   {
        switch( devType ) {
            case LedMockObj:   return new LedMock();
            case LedGuiObj:    return LedAsGui.createLed();
            case LedOnArduino: return new LedProxyArduino(
                    DeviceConfig.serialPortNum, DeviceConfig.serialBaudrate );
            default: return null;
        }
    }
    public IButtonObservable createButton(ButtonType  devType )   {
        switch( devType ) {
            case ButtonMockObj: return new ButtonMock();
            case ButtonGuiObj: return  ButtonAsGui.createButton("CLICKME");
            case ButtonOnArduino: return new ButtonProxyArduino(
                    DeviceConfig.serialPortNum, DeviceConfig.serialBaudrate );
            default: return null;
        }
    }

}
