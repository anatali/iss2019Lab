package it.unibo.blsFramework.interfaces;

import it.unibo.bls.interfaces.IObserver;
import it.unibo.blsFramework.kotlin.fsm.Messages;
import kotlinx.coroutines.channels.SendChannel;

public interface IButtonListener extends IObserver{
    public void setControl( SendChannel<Messages> channel );
    public int getNumOfClicks();
}
