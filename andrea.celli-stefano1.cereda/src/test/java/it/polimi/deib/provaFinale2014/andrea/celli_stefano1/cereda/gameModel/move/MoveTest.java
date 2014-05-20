/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import static org.junit.Assert.*;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import org.junit.Test;

/**
 * @author stefano
 * 
 */
public class MoveTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move#Move(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player)}
	 * .
	 */
	@Test
	public void testMove() {
		Player p = null;
		Move m = new Move(p);
		assertNotNull(m);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move#getPlayer()}
	 * .
	 */
	@Test
	public void testGetPlayer() {
		Player p1 = new Player();
		Move m = new Move(p1);
		Player p2 = m.getPlayer();
		assertEquals(p1, p2);

	}

}
