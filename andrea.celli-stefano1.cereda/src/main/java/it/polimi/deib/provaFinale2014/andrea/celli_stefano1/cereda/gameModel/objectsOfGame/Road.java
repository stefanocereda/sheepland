package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.GenericGameObject;

import java.util.ArrayList;
import java.util.List;

/***
 * Each road has an HashSet containing its adjacent roads, an array containing
 * the two adjacent terrains and the road's numeric value.
 * 
 * @author Andrea Celli
 * 
 */

public class Road extends GenericGameObject {
	private static final long serialVersionUID = -4591369994622514357L;
	/** A list of next roads */
	private List<Road> nextRoads = new ArrayList<Road>();
	/** An array of adjacent terrains */
	private Terrain[] adjacentTerrains;
	/** The value of the box on the road */
	private int boxValue;

	/**
	 * The constructor of a road sets the values:
	 * 
	 * @param boxValue
	 *            The value of the box on the road
	 * @param terrain1
	 *            The first adjacent terrain
	 * @param terrain2
	 *            The second adjacent terrain
	 */
	public Road(int boxValue, Terrain terrain1, Terrain terrain2) {
		this.boxValue = boxValue;
		adjacentTerrains = new Terrain[2];
		adjacentTerrains[0] = terrain1;
		adjacentTerrains[1] = terrain2;
	}

	/** add an adjacent road to the list */
	public void add(Road newRoad) {
		nextRoads.add(newRoad);
	}

	/** Get the list of next roads */
	public List<Road> getNextRoads() {
		return nextRoads;
	}

	/** Get the array of adjacent terrains */
	public Terrain[] getAdjacentTerrains() {
		return adjacentTerrains;
	}

	/** Get the value of the box on the road */
	public int getBoxValue() {
		return boxValue;
	}

}
