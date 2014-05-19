/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Terrain;

import org.junit.Test;

/**
 * @author stefano
 * 
 */
public class MoveBlackSheepTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveBlackSheep#MoveBlackSheep(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Terrain)}
	 * .
	 */
	@Test
	public void testMoveBlackSheep() {
		MoveBlackSheep mbs = new MoveBlackSheep(null, null, null);
		assertNotNull(mbs);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveBlackSheep#getNewPositionOfTheBlackSheep()}
	 * .
	 */
	@Test
	public void testGetNewPositionOfTheBlackSheep() {
		for (Terrain t : Terrain.values()) {
			MoveBlackSheep mbs = new MoveBlackSheep(null, t, null);
			assertEquals(t, mbs.getNewPositionOfTheBlackSheep());
		}
	}

}
