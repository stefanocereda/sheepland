package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame;

import java.io.Serializable;

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

	public String toString() {
		if (this == Terrain.P1)
			return "p1";
		if (this == Terrain.P2)
			return "p2";
		if (this == Terrain.P3)
			return "p3";
		if (this == Terrain.C1)
			return "c1";
		if (this == Terrain.C2)
			return "c2";
		if (this == Terrain.C3)
			return "c3";
		if (this == Terrain.D1)
			return "d1";
		if (this == Terrain.D2)
			return "d2";
		if (this == Terrain.D3)
			return "d3";
		if (this == Terrain.L1)
			return "l1";
		if (this == Terrain.L2)
			return "l2";
		if (this == Terrain.L3)
			return "l3";
		if (this == Terrain.M1)
			return "m1";
		if (this == Terrain.M2)
			return "m2";
		if (this == Terrain.M3)
			return "m3";
		if (this == Terrain.W1)
			return "w1";
		if (this == Terrain.W2)
			return "w2";
		if (this == Terrain.W3)
			return "w3";

		return null;
	}

}
