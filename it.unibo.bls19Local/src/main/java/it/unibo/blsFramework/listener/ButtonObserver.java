package it.unibo.blsFramework.listener;

import it.unibo.blsFramework.interfaces.IAppLogic;
import it.unibo.blsFramework.interfaces.IApplListener;
import java.util.Observable;

public class ButtonObserver implements IApplListener {
private IAppLogic controller;
	private int count   = 0;
//Factory method
	public static ButtonObserver createButtonListener(  ){
		return new ButtonObserver(   );
	}
	private ButtonObserver(  ){
		super();
 	}

	public void setControl( IAppLogic ctrl ){
		this.controller = ctrl;
	}

	public int getNumOfClicks(){
		return count;
	}

	@Override  //from IButtonListener IObserver -> Observer
	public void update( Observable source, Object state ){
		count++;
		//System.out.println("ButtonListener update | state=" + state + " controller=" + controller);
		controller.execute ( state.toString() );
	}
}
