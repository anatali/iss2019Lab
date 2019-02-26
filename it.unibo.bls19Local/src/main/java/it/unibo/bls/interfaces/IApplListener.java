package it.unibo.bls.interfaces;

public interface IApplListener extends IObserver{
    public void setControl( IControl ctrl );
    public int getNumOfClicks();
}
