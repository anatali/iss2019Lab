package it.unibo.bls.kotlin.devices

import it.unibo.bls.kotlin.devices.ButtonType
import it.unibo.bls.kotlin.devices.LedType
import it.unibo.bls.kotlin.devices.mock.LedMock
import it.unibo.bls.kotlin.interfaces.IButtonObservable
import it.unibo.bls.kotlin.interfaces.ILed
import it.unibo.bls.kotlin.devices.mock.ButtonMockCoroutine

class DeviceFactory {

    fun createLed(devType: LedType): ILed? {
        when (devType) {
            LedType.LedMockObj -> return LedMock()
            else -> return null
        }
    }

    fun createButton(devType: ButtonType): IButtonObservable? {
        when (devType) {
            ButtonType.ButtonMockObj -> return ButtonMockCoroutine()
            else -> return null
        }
    }

}
