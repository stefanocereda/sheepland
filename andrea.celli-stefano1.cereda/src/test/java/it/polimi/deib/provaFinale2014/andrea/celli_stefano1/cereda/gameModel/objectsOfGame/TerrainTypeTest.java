package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test for the TerrainType
 * 
 * @author Andrea
 * 
 */
public class TerrainTypeTest {

	@Test
	public void toStringTest() {

		for (TerrainType type : TerrainType.values()) {
			if (type.equals(TerrainType.COUNTRYSIDE)) {
				assertEquals(type.toString(), "countryside");
			}
			if (type.equals(TerrainType.DESERT)) {
				assertEquals(type.toString(), "desert");
			}
			if (type.equals(TerrainType.PLAIN)) {
				assertEquals(type.toString(), "plain");
			}
			if (type.equals(TerrainType.LAKE)) {
				assertEquals(type.toString(), "lake");
			}
			if (type.equals(TerrainType.MOUNTAIN)) {
				assertEquals(type.toString(), "mountain");
			}
			if (type.equals(TerrainType.SHEEPSBURG)) {
				assertEquals(type.toString(), "sheepsburg");
			}
			if (type.equals(TerrainType.WOOD)) {
				assertEquals(type.toString(), "wood");
			}

		}
	}
}
