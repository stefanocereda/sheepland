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
public class RoadTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Road#Road(int, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Terrain, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Terrain)}
	 * .
	 */
	@Test
	public void testRoad() {
		Road road = new Road(0, null, null);
		assertNotNull(road);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Road#add(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Road)}
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Road#getNextRoads()}
	 */
	@Test
	public void testAdjacentRoads() {
		Road r1 = new Road(0, null, null);
		Road r2 = new Road(0, null, null);

		r1.add(r2);
		assertTrue(r1.getNextRoads().contains(r2));
		assertFalse(r2.getNextRoads().contains(r1));
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Road#getAdjacentTerrains()}
	 * .
	 */
	@Test
	public void testGetAdjacentTerrains() {
		Terrain t1 = Terrain.C1;
		Terrain t2 = Terrain.C2;
		Terrain t3 = Terrain.C3;

		Road r = new Road(0, t1, t2);

		boolean t1contained = false, t2contained = false, t3contained = false;
		Terrain[] contained = r.getAdjacentTerrains();

		for (Terrain t : contained) {
			if (t == t1)
				t1contained = true;
			if (t == t2)
				t2contained = true;
			if (t == t3)
				t3contained = true;
		}

		assertTrue(t1contained);
		assertTrue(t2contained);
		assertFalse(t3contained);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Road#getBoxValue()}
	 * .
	 */
	@Test
	public void testGetBoxValue() {
		Road r = new Road(15, null, null);
		assertEquals(r.getBoxValue(), 15);
	}

}
