/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class RoadMapTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.RoadMap#getRoadMap()}
	 * .
	 */
	@Test
	public void testGetRoadMap() {
		RoadMap rm1 = RoadMap.getRoadMap();
		rm1.setID();
		RoadMap rm2 = RoadMap.getRoadMap();
		rm2.setID();

		assertNotNull(rm1);
		assertEquals(rm1, rm2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.RoadMap#getHashMapOfRoads()}
	 * .
	 */
	@Test
	public void testGetHashSetOfRoads() {
		RoadMap rm = RoadMap.getRoadMap();
		rm.setID();
		Map<Integer, Road> roads = rm.getHashMapOfRoads();
		assertNotNull(roads);
	}

	@Test
	public void findRoadsAdjacentToATerrainTest() {
		Set<Road> adjacentRoad = new HashSet<Road>();
		RoadMap map = RoadMap.getRoadMap();
		// roads adjacents to the terrain d3
		adjacentRoad.add(map.getHashMapOfRoads().get(26));
		adjacentRoad.add(map.getHashMapOfRoads().get(27));
		adjacentRoad.add(map.getHashMapOfRoads().get(35));

		Set<Road> roads = map.findRoadsAdjacentToATerrain(Terrain.D3);
		assertEquals(adjacentRoad, roads);
	}

	@Test
	public void testNextRoads() {
		RoadMap roadMap = RoadMap.getRoadMap();

		Road r5 = roadMap.getHashMapOfRoads().get(5);
		Road r6 = roadMap.getHashMapOfRoads().get(6);

		assertTrue(r5.getNextRoads().contains(r6));
	}
}
