package itunibo.coap;
import org.eclipse.californium.core.server.resources.ResourceObserver;

//import it.unibo.bls.interfaces.IObserver;

public interface IResourceLocalGofObserver extends ResourceObserver{
	public void update( String v );
}
