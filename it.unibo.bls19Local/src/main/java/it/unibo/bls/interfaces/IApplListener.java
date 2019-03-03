package it.unibo.bls.interfaces;

import it.unibo.blsFramework.interfaces.IAppLogic;

public interface IApplListener extends IObserver{
    public void setControl(IControlLed ctrl);
    public int getNumOfClicks();
}
