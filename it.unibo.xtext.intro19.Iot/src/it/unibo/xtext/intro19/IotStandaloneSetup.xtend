/*
 * generated by Xtext 2.16.0
 */
package it.unibo.xtext.intro19


/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
class IotStandaloneSetup extends IotStandaloneSetupGenerated {

	def static void doSetup() {
		new IotStandaloneSetup().createInjectorAndDoEMFRegistration()
	}
}
