package itunibo.coap;
import org.eclipse.californium.core.server.resources.Resource;

public interface IResourceIot extends Resource {
	public void setObserver( IResourceLocalGofObserver obs );
	public void setValue(String v);
}
