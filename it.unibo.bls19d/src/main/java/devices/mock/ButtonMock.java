package devices.mock;


import devices.interfaces.IObserver;
import devices.utils.Utils;
import devices.interfaces.IButtonObservable;
import java.util.Observable;

public class ButtonMock extends Observable implements IButtonObservable {
private boolean buttonState = false;

//Factory method
public static IButtonObservable createButton( ){
	 ButtonMock button = new ButtonMock();
	 return button;
}
	@Override //from Observable and IButtonObservable
	public void addObserver( IObserver observer ) {
		super.addObserver(observer);
		new Thread(){
			public void run(){
				simulateUserInteraction();
			}
		}.start();
	}
/*
Autnnomous behavior
 */
	private void simulateUserInteraction(){
		System.out.println("	ButtonMock | simulateUserInteraction ...");
		for( int i = 1; i <= 4; i++ ){
			buttonState = ! buttonState;
			System.out.println("	ButtonMock | click num=" + i);
			this.setChanged();
			this.notifyObservers("click");
			Utils.delay(1000);
		}
	}
}
