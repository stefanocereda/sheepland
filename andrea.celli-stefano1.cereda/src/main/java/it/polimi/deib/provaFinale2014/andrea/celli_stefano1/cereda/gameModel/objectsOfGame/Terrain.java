package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This enum defines all the terrains, each terrain has a type and a number that
 * allows to identify it
 * 
 * @author Andrea Celli
 * 
 */
public enum Terrain implements Serializable {
	P1(TerrainType.PLAIN, 1), P2(TerrainType.PLAIN, 2), P3(TerrainType.PLAIN, 3), C1(
			TerrainType.COUNTRYSIDE, 1), C2(TerrainType.COUNTRYSIDE, 2), C3(
			TerrainType.COUNTRYSIDE, 3), M1(TerrainType.MOUNTAIN, 1), M2(
			TerrainType.MOUNTAIN, 2), M3(TerrainType.MOUNTAIN, 3), D1(
			TerrainType.DESERT, 1), D2(TerrainType.DESERT, 2), D3(
			TerrainType.DESERT, 3), L1(TerrainType.LAKE, 1), L2(
			TerrainType.LAKE, 2), L3(TerrainType.LAKE, 3), W1(TerrainType.WOOD,
			1), W2(TerrainType.WOOD, 2), W3(TerrainType.WOOD, 3), SHEEPSBURG(
			TerrainType.SHEEPSBURG, 0);

	private final TerrainType type;
	private final int terrainNumber;

	private Terrain(TerrainType type, int terrainNumber) {
		this.type = type;
		this.terrainNumber = terrainNumber;
	}

	public TerrainType getTerrainType() {
		return type;
	}

	@Override
	public String toString() {
		return this.name().toLowerCase();
	}

	/**
	 * @param roadmap
	 *            A roadmap
	 * @return a list of adjacent terrains
	 */
	public List<Terrain> getAdjacentTerrains(RoadMap roadmap) {
		List<Terrain> adjacent = new ArrayList<Terrain>();

		Set<Road> availableRoads = roadmap.findRoadsAdjacentToATerrain(this);

		for (Road r : availableRoads) {
			for (Terrain t : r.getAdjacentTerrains()) {
				adjacent.add(t);
			}
		}

		return adjacent;
	}

	/**
	 * Search for the road that is linking two terrains
	 * 
	 * @param other
	 *            The other terrain
	 * @param roadMap
	 *            a RoadMap
	 * @return the linking road
	 */
	public Road getLinkWith(Terrain other, RoadMap roadMap) {
		Set<Road> availableRoads = roadMap.findRoadsAdjacentToATerrain(this);

		for (Road r : availableRoads) {
			for (Terrain t : r.getAdjacentTerrains()) {
				if (t.equals(other)) {
					return r;
				}
			}
		}
		return null;
	}
}
