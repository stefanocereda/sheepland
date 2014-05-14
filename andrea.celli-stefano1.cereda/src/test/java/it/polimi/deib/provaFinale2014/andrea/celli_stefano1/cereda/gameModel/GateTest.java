/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class GateTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Gate#Gate(boolean, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Road)}
	 * .
	 */
	@Test
	public void testGate() {
		Gate g = new Gate(true, null);
		assertNotNull(g);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Gate#isLast()}
	 * .
	 */
	@Test
	public void testIsLast() {
		Gate gt = new Gate(true, null);
		Gate gf = new Gate(false, null);
		assertTrue(gt.isLast());
		assertFalse(gf.isLast());
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Gate#getPosition()}
	 * .
	 */
	@Test
	public void testGetPosition() {
		// TODO, must tests all the possible roads, needs a getAllRoads from RoadMap

	}
}