package it.unibo.bls.devices.gui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import it.unibo.bls.interfaces.IButtonObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.utils.Utils;

/*
Java does not support multiple inheritance among classes.
Thus we
 */
public class ButtonAsGui extends Observable implements IButtonObservable, ActionListener{
	private Frame frame;
	private String cmd;
//Factory method
public static ButtonAsGui createButton(  String cmd  ){
	ButtonAsGui button = new ButtonAsGui();
	ButtonBasic bb     = new ButtonBasic(Utils.initFrame(600,600), cmd, button);	//button is the listener
	return button;
}
	@Override //from Observable and IButtonObservable
	public void addObserver(IObserver observer) {
		super.addObserver(observer);
	}
	@Override  //from ActionListener
	public void actionPerformed(ActionEvent e) {
		//System.out.println("	ButtonAsGui | actionPerformed ..." + e.getActionCommand() );
		this.setChanged();
		this.notifyObservers(e.getActionCommand());
	}
}
