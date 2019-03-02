package it.unibo.blsFramework.interfaces;

import it.unibo.bls.interfaces.IControlLed;
import it.unibo.bls.interfaces.IObserver;

public interface IApplListener extends IObserver{
    public void setControl(IAppLogic ctrl);
    public int getNumOfClicks();
}
