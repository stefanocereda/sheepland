/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

import static org.junit.Assert.*;

import java.util.HashMap;
import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class RoadMapTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.RoadMap#getRoadMap()}
	 * .
	 */
	@Test
	public void testGetRoadMap() {
		RoadMap rm1 = RoadMap.getRoadMap();
		RoadMap rm2 = RoadMap.getRoadMap();

		assertNotNull(rm1);
		assertEquals(rm1, rm2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.RoadMap#getHashMapOfRoads()}
	 * .
	 */
	@Test
	public void testGetHashSetOfRoads() {
		RoadMap rm = RoadMap.getRoadMap();
		HashMap<Integer, Road> roads = rm.getHashMapOfRoads();
		assertNotNull(roads);
	}

}
