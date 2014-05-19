package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

import java.util.HashSet;

/***
 * Each road has an HashSet containing its adjacent roads, an array containing
 * the two adjacent terrains and the road's numeric value.
 * 
 * @author Andrea Celli
 * 
 */

public class Road {
	private HashSet<Road> nextRoads = new HashSet<Road>();
	private Terrain[] adjacentTerrains;
	private int boxValue;

	public Road(int boxValue, Terrain terrain1, Terrain terrain2) {
		this.boxValue = boxValue;
		adjacentTerrains = new Terrain[2];
		adjacentTerrains[0] = terrain1;
		adjacentTerrains[1] = terrain2;
	}

	// add an adjacent road to the HashSet
	public void add(Road newRoad) {
		nextRoads.add(newRoad);
	}

	public HashSet<Road> getNextRoads() {
		return nextRoads;
	}

	public Terrain[] getAdjacentTerrains() {
		return adjacentTerrains;
	}

	public int getBoxValue() {
		return boxValue;
	}

}
