package devices.gui;

import devices.interfaces.IButtonObservable;
import devices.interfaces.IObserver;
import devices.utils.Utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

/*
Java does not support multiple inheritance among classes.
Thus we
 */
public class ButtonAsGui extends Observable implements IButtonObservable, ActionListener{
	private Frame frame;
	private String cmd;
//Factory method
public static ButtonAsGui createButton(String cmd  ){
	ButtonAsGui button = new ButtonAsGui();
	ButtonBasic bb     = new ButtonBasic(Utils.initFrame(400,400), cmd, button);	//button is the listener
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
