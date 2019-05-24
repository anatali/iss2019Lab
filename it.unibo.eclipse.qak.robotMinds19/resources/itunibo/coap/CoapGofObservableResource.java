package itunibo.coap;

import org.eclipse.californium.core.CoapResource;

public abstract class CoapGofObservableResource extends CoapResource implements IResourceIot{
protected IResourceLocalGofObserver observer  ;
	
	public CoapGofObservableResource(String name) {
		super(name);
 	}
	
	public void setObserver( IResourceLocalGofObserver obs ) {
		observer = obs;
	}
	/*
	 * To be defined by the application designer.
	 * It can be used by physical devices to change the model
	 */
	public abstract void setValue(String v);
	
	protected void update(String v) {
		System.out.println("CoapGofObservableResource update v="+ v + " observer=" + observer);
		if( observer != null ) observer.update(v);
	}

}
