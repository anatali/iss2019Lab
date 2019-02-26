package it.unibo.bls.devices.gui;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionListener;
import it.unibo.bls.interfaces.IButton;

public class ButtonBasic extends java.awt.Button {
	public ButtonBasic(Frame frame, String label, ActionListener listener){
		super(label);
		this.addActionListener(  listener );
		frame.add(BorderLayout.WEST,this); 
		frame.validate();
	}
}