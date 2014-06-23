package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame;

import java.io.Serializable;

/**
 * This enum defines the types of terrain
 * 
 * @author Andrea Celli
 * 
 */
public enum TerrainType implements Serializable {
	PLAIN, COUNTRYSIDE, MOUNTAIN, DESERT, WOOD, LAKE, SHEEPSBURG;

	/**
	 * @return the string name of the enum value (lower case letters)
	 */
	@Override
	public String toString() {
		return this.name().toLowerCase();
	}

}
