/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * @author stefano
 * 
 */
public class MovePlayerTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer#MovePlayer(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Road)}
	 * .
	 */
	@Test
	public void testMovePlayer() {
		MovePlayer mp = new MovePlayer(null, null);
		mp.setID();
		assertNotNull(mp);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer#getNewPositionOfThePlayer()}
	 * .
	 */
	@Test
	public void testGetNewPositionOfThePlayer() {
		// TODO need a "getAllTheRoads"
	}

}
