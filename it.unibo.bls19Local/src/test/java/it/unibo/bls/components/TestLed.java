package it.unibo.bls.components;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import it.unibo.bls.devices.mock.LedMock;
import it.unibo.bls.interfaces.ILed;
 
public class TestLed {
private ILed led;

	@Before
	public void setUp(){
		led = LedMock.createLed();
		assertTrue("setUp", ! led.getState() );
	}
 	@Test
	public void testTheLedOn(){
 		led.turnOn();
		assertTrue("testTheLedOn", led.getState() );
  	}
	@Test
	public void testTheLedOff(){
 		led.turnOn();
		assertTrue(  led.getState() );
 		led.turnOff();
		assertTrue( ! led.getState() );
	}
	
}
