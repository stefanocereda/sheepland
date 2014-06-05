package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame;

import java.io.Serializable;

/**
 * Enum of all the possible cards, including initial ones shared between all the
 * games
 * 
 * @author Stefano
 * 
 */
public enum Card implements Serializable {
	PLAINi(TerrainType.PLAIN, true, 0), PLAIN0(TerrainType.PLAIN, false, 0), PLAIN1(
			TerrainType.PLAIN, false, 1), PLAIN2(TerrainType.PLAIN, false, 2), PLAIN3(
			TerrainType.PLAIN, false, 3), PLAIN4(TerrainType.PLAIN, false, 4),

	COUNTRYSIDEi(TerrainType.COUNTRYSIDE, true, 0), COUNTRYSIDE0(
			TerrainType.COUNTRYSIDE, false, 0), COUNTRYSIDE1(
			TerrainType.COUNTRYSIDE, false, 1), COUNTRYSIDE2(
			TerrainType.COUNTRYSIDE, false, 2), COUNTRYSIDE3(
			TerrainType.COUNTRYSIDE, false, 3), COUNTRYSIDE4(
			TerrainType.COUNTRYSIDE, false, 4),

	MOUNTAINi(TerrainType.MOUNTAIN, true, 0), MOUNTAIN0(TerrainType.MOUNTAIN,
			false, 0), MOUNTAIN1(TerrainType.MOUNTAIN, false, 1), MOUNTAIN2(
			TerrainType.MOUNTAIN, false, 2), MOUNTAIN3(TerrainType.MOUNTAIN,
			false, 3), MOUNTAIN4(TerrainType.MOUNTAIN, false, 4),

	DESERTi(TerrainType.DESERT, true, 0), DESERT0(TerrainType.DESERT, false, 0), DESERT1(
			TerrainType.DESERT, false, 1), DESERT2(TerrainType.DESERT, false, 2), DESERT3(
			TerrainType.DESERT, false, 3), DESERT4(TerrainType.DESERT, false, 4),

	WOODi(TerrainType.WOOD, true, 0), WOOD0(TerrainType.WOOD, false, 0), WOOD1(
			TerrainType.WOOD, false, 1), WOOD2(TerrainType.WOOD, false, 2), WOOD3(
			TerrainType.WOOD, false, 3), WOOD4(TerrainType.WOOD, false, 4),

	LAKEi(TerrainType.LAKE, true, 0), LAKE0(TerrainType.LAKE, false, 0), LAKE1(
			TerrainType.LAKE, false, 1), LAKE2(TerrainType.LAKE, false, 2), LAKE3(
			TerrainType.LAKE, false, 3), LAKE4(TerrainType.LAKE, false, 4);

	/** The type of terrain of the card */
	private TerrainType terrainType;
	/** If the card is an initial one */
	private boolean initial;
	/** The number of the card */
	private int number;

	private Card(TerrainType type, boolean init, int num) {
		terrainType = type;
		initial = init;
		number = num;
	}

	/**
	 * @return the terrainType
	 */
	public TerrainType getTerrainType() {
		return terrainType;
	}

	/**
	 * @return if it's an initial card
	 */
	public boolean isInitial() {
		return initial;
	}

	/**
	 * @return the number on the card (cost)
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @return the string rapresenting a card
	 */
	@Override
	public String toString() {
		return this.terrainType.toString() + " " + this.number;
	}

	/**
	 * @return the name of the image of the specified card
	 */
	public String getImageName() {
		return "/" + this.getTerrainType().toString() + this.number + ".png";
	}

}
