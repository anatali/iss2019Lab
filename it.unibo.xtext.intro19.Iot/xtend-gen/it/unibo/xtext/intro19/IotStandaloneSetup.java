/**
 * generated by Xtext 2.16.0
 */
package it.unibo.xtext.intro19;

import it.unibo.xtext.intro19.IotStandaloneSetupGenerated;

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
@SuppressWarnings("all")
public class IotStandaloneSetup extends IotStandaloneSetupGenerated {
  public static void doSetup() {
    new IotStandaloneSetup().createInjectorAndDoEMFRegistration();
  }
}