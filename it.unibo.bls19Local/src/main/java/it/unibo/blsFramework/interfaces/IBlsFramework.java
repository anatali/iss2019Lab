package it.unibo.blsFramework.interfaces;

import it.unibo.bls.interfaces.ILed;
import it.unibo.bls.interfaces.IObservable;
import it.unibo.bls.interfaces.IObserver;

public interface IBlsFramework {
    public void addConcreteButton(IObservable button);
    public void setConcreteLed( ILed led );
    public void addConcreteLed( ILed led );
    public void setApplLogic(  IAppLogic appLogic );
}
