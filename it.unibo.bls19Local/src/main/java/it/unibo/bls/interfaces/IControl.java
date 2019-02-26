package it.unibo.bls.interfaces;

public interface IControl {
    public void execute( String cmd );
    public int getNumOfCalls(); //useful for testing
}
