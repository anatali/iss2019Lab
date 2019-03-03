package it.unibo.blsFramework.interfaces;

import it.unibo.bls.interfaces.ILed;
import it.unibo.bls.interfaces.IObservable;


public interface IBlsFramework {
    public void addConcreteButton(IObservable button);
    public void setConcreteLed( ILed led );
    public void addConcreteLed( ILed led );
    public void setApplLogic(  IAppLogic appLogic );

    public ILedModel getLedModel();
    public IButtonModel getButtonModel();
    public ILed getLedConcrete();
    public IObservable getButtonConcrete();
    public IAppLogic getApplLogic();
    public IApplListener getButtonObserver();
}
