package it.unibo.blsFramework.interfaces;

import it.unibo.bls.interfaces.IObservable;
import it.unibo.bls.interfaces.IObserver;

public interface IBlsFramework {
    public void setConcreteLed(IObserver led);
    public void setConcreteButton(IObservable button);
    public void setApplLogic(  IAppLogic appLogic );
}
