package it.unibo.blsFramework.concreteDevices.gui;

import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.utils.Utils;
import java.awt.*;
import java.util.Observable;

public class LedGui implements IObserver  {
	private Panel p ;
	private Frame frame;

private final Dimension sizeOn  = new Dimension(100,100);
private final Dimension sizeOff = new Dimension(30,30);

	public static IObserver createLed(  ){
		LedGui led = new LedGui(Utils.initFrame(200,200));
		led.turnOff();
		return led;
	}

	public LedGui(Frame frame ) {
		super();
		this.frame = frame;
 		configure( );
  	}
	protected void configure( ){
		p = new Panel();
		p.setSize( sizeOff );
		p.validate();
		p.setBackground(Color.red);
		p.validate();
		frame.add(BorderLayout.CENTER,p);
  	}
	@Override //IObserver
	public void update(Observable o, Object arg) {
		boolean state = arg.toString().equals("true");
		if( state ) turnOn(); else turnOff();
	}
	public void turnOn(){
 		p.setSize( sizeOn );
		p.validate();
	}
	public void turnOff() {
 		p.setSize( sizeOff );
		p.validate();
	}
 }
