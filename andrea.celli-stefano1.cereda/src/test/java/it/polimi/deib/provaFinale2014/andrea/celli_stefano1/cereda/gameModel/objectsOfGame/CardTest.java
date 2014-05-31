/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class CardTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card#getTerrainType()}
	 * .
	 */
	@Test
	public void testGetTerrainType() {
		// tests the terrain by the name
		for (Card c : Card.values()) {
			if (c.name().contains("COUNTRYSIDE")) {
				assertEquals(c.getTerrainType(), TerrainType.COUNTRYSIDE);
			} else if (c.name().contains("DESERT")) {
				assertEquals(c.getTerrainType(), TerrainType.DESERT);
			} else if (c.name().contains("LAKE")) {
				assertEquals(c.getTerrainType(), TerrainType.LAKE);
			} else if (c.name().contains("MOUNTAIN")) {
				assertEquals(c.getTerrainType(), TerrainType.MOUNTAIN);
			} else if (c.name().contains("PLAIN")) {
				assertEquals(c.getTerrainType(), TerrainType.PLAIN);
			} else if (c.name().contains("WOOD")) {
				assertEquals(c.getTerrainType(), TerrainType.WOOD);
			}
		}
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card#isInitial()}
	 * .
	 */
	@Test
	public void testIsInitial() {
		// tests the initials knowing that their names end with an "i"
		for (Card c : Card.values()) {
			if (c.name().contains("i")) {
				assertTrue(c.isInitial());
			} else {
				assertFalse(c.isInitial());
			}
		}
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card#getNumber()}
	 * .
	 */
	@Test
	public void testGetNumber() {
		// tests the card values by their names
		for (Card c : Card.values()) {
			if (c.name().contains("i")) {
				assertEquals(c.getNumber(), 0);
			} else if (c.name().contains("0")) {
				assertEquals(c.getNumber(), 0);
			} else if (c.name().contains("1")) {
				assertEquals(c.getNumber(), 1);
			} else if (c.name().contains("2")) {
				assertEquals(c.getNumber(), 2);
			} else if (c.name().contains("3")) {
				assertEquals(c.getNumber(), 3);
			} else if (c.name().contains("4")) {
				assertEquals(c.getNumber(), 4);
			}
		}
	}

}
