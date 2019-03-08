package it.unibo.bls.devices.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Panel;
import it.unibo.bls.devices.mock.LedMock;
import it.unibo.bls.interfaces.ILed;
import it.unibo.bls.utils.Utils;

public class LedAsGui extends LedMock { //
private Panel p ; 
private Frame frame;
private final Dimension sizeOn  = new Dimension(100,100);
private final Dimension sizeOff = new Dimension(30,30);

	public static ILed createLed(  ){
		LedAsGui led = new LedAsGui(Utils.initFrame(400,400));
		led.turnOff();
		return led;
	}
	public static ILed createLed( Frame frame){
		LedAsGui led = new LedAsGui(frame);
		led.turnOff();
		return led;
	}
	//Constructor
	public LedAsGui( Frame frame ) {
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
	@Override //LedMock
	public void turnOn(){
		state = true;
		p.setSize( sizeOn );
		p.validate();
	}
	@Override //LedMock
	public void turnOff() {
		state = false;
		p.setSize( sizeOff );
		p.validate();
	}
}
