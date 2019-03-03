package it.unibo.blsFramework.interfaces;

import it.unibo.bls.interfaces.ILed;
import it.unibo.bls.interfaces.IObserver;

public interface ILedObserver extends IObserver {
    public void setLed( ILed led );
}
