/*
 * generated by Xtext 2.16.0
 */
package it.unibo.xtext.intro19.validation;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;

public abstract class AbstractIotValidator extends AbstractDeclarativeValidator {
	
	@Override
	protected List<EPackage> getEPackages() {
		List<EPackage> result = new ArrayList<EPackage>();
		result.add(it.unibo.xtext.intro19.iot.IotPackage.eINSTANCE);
		return result;
	}
}