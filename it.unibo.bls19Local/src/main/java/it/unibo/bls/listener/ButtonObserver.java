package it.unibo.bls.listener;

import it.unibo.bls.interfaces.IButtonListener;
import it.unibo.bls.interfaces.IControlLed;
import java.util.Observable;

public class ButtonObserver  implements IButtonListener {
private IControlLed controller;
	private int count   = 0;
//Factory method
	public static ButtonObserver createButtonListener(  ){
		return new ButtonObserver(   );
	}
	private ButtonObserver(  ){
		super();
 	}
	public void setControl( IControlLed ctrl ){
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
