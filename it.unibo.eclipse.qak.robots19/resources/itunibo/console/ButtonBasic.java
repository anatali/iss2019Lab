package itunibo.console;
import java.awt.Frame;
import java.awt.event.ActionListener;
 
public class ButtonBasic extends java.awt.Button {
	public ButtonBasic(Frame frame, String label, ActionListener listener){
		super(label);
		this.addActionListener(  listener );
		frame.add(this); 
		frame.validate();
		frame.isVisible();
		//System.out.println("BUTTON BASIC CREATED");
	}
}