package itunibo.coap;
import org.eclipse.californium.core.observe.ObserveRelation;
import org.eclipse.californium.core.server.resources.Resource;

public abstract class ResourceLocalGofObserver implements IResourceLocalGofObserver{
   
  	protected void showMsg(String msg) {
 		 System.out.println( msg ) ;
 	}

	@Override
	public void changedName(String old) { 		
	}
	@Override
	public void changedPath(String old) {		
	}
	@Override
	public void addedChild(Resource child) {		
	}
	@Override
	public void removedChild(Resource child) {		
	}
	@Override
	public void addedObserveRelation(ObserveRelation relation) { 		
	}
	@Override
	public void removedObserveRelation(ObserveRelation relation) {		
	}
}
