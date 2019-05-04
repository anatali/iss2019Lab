package devices.gui;

import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonBasic extends Button {
	public ButtonBasic(Frame frame, String label, ActionListener listener){
		super(label);
		this.addActionListener(  listener );
		frame.add(BorderLayout.WEST,this); 
		frame.validate();
	}
}