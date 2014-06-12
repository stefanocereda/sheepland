/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class InterfaceCreatorTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.InterfaceCreator#create(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.TypeOfInterface)}
	 * .
	 */
	@Test
	public void testCreate() {
		List<Interface> interfaces = new ArrayList<Interface>();

		for (TypeOfInterface type : TypeOfInterface.values()) {
			Interface created = InterfaceCreator.create(type);
			assertNotNull(created);
			assertFalse(interfaces.contains(created));
			interfaces.add(created);
		}
		
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
