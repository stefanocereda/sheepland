/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import static org.junit.Assert.*;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

import org.junit.Test;

/**
 * @author stefano
 * 
 */
public class MoveSheepTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep#MoveSheep(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain)}
	 * .
	 */
	@Test
	public void testMoveSheep() {
		MoveSheep ms = new MoveSheep(null, null, null);
		assertNotNull(ms);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep#getMovedSheep()}
	 * .
	 */
	@Test
	public void testGetMovedSheep() {
		Sheep s = new Sheep(null);
		MoveSheep ms = new MoveSheep(null, s, null);
		assertEquals(s, ms.getMovedSheep());
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep#getNewPositionOfTheSheep()}
	 * .
	 */
	@Test
	public void testGetNewPositionOfTheSheep() {
		for (Terrain t : Terrain.values()) {
			MoveSheep ms = new MoveSheep(null, null, t);
			assertEquals(t, ms.getNewPositionOfTheSheep());
		}
	}

}
