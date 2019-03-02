package it.unibo.blsFramework.interfaces;

public interface IAppLogic {
    public void setControlled(ILedModel obj);
    public void execute(String cmd);
    public int getNumOfCalls(); //useful for testing
}
