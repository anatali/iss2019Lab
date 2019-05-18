package itunibo.console;

import java.awt.Frame;
import java.awt.GridLayout;
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

//Factory method
public static ButtonAsGui createButton(  String[] cmd  ){
	Frame fr = Utils.initFrame(300,300);
	fr.setLayout(new GridLayout(2,3));
	ButtonAsGui button = new ButtonAsGui();
	for( int i=0; i<cmd.length;i++)
		new ButtonBasic(fr, cmd[i], button);	//button is the listener
	return button;
}
	@Override //from Observable and IButtonObservable
	public void addObserver(IObserver observer) {
		super.addObserver(observer);
	}
	@Override  //from ActionListener
	public void actionPerformed(ActionEvent e) {
		this.setChanged();
		this.notifyObservers(e.getActionCommand());
	}
}
