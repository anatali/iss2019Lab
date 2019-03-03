package it.unibo.bls.interfaces;

import it.unibo.blsFramework.interfaces.IAppLogic;

public interface IButtonListener extends IObserver{
    public void setControl(IControlLed ctrl);
    public int getNumOfClicks();
}
