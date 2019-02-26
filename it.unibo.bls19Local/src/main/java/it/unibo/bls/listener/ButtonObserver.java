package it.unibo.bls.listener;

import it.unibo.bls.interfaces.IApplListener;
import it.unibo.bls.interfaces.IControl;

import java.awt.event.ActionEvent;
import java.util.Observable;

public class ButtonObserver  implements IApplListener {
private IControl controller;
	private int count   = 0;
//Factory method
	public static ButtonObserver createButtonListener(  ){
		return new ButtonObserver(   );
	}

	private ButtonObserver(  ){
		super();
 	}
	public void setControl( IControl ctrl ){
		this.controller = ctrl;
	}

	public int getNumOfClicks(){
		return count;
	}
 	@Override  //from IObserver -> Observer
	public void update( Observable source, Object state ){
		count++;
		//System.out.println("ButtonListener update | state=" + state + " controller=" + controller);
		controller.execute ( state.toString() );
	}
}
