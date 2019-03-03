package it.unibo.blsFramework.models;

import java.util.Observable;

import it.unibo.bls.interfaces.IObserver;
import it.unibo.blsFramework.interfaces.IApplListener;
import it.unibo.blsFramework.interfaces.IButtonModel;

public class ButtonModel extends Observable implements IButtonModel {
private boolean buttonState = false;
private String btnName;
private IApplListener buttonObserver;

//Factory methods
public static ButtonModel createButton( String btnName ){
	 ButtonModel button = new ButtonModel(btnName);
 	 return button;
}

	public ButtonModel(String btnName) {
		this.btnName = btnName;
	}

	@Override  //from Observable and IButtonObservable
	public void addObserver(IObserver observer) {
		super.addObserver(observer);
	}
	
	@Override //for IObserver (called by the lower layer)
	public void update(Observable source, Object value) {
		//System.out.println("	ButtonModel | updated"   );
		switchThestate();
	}

	protected void switchThestate() {
		buttonState = ! buttonState;
		this.setChanged();
		this.notifyObservers(btnName);				
	}
}
