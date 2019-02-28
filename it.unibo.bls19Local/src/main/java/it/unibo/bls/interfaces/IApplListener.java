package it.unibo.bls.interfaces;

public interface IApplListener extends IObserver{
    public void setControl( IControlLed ctrl );
    public int getNumOfClicks();
}
