/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import static org.junit.Assert.*;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameController;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameControllerCreator;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameType;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.DisconnectedClient;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class DisconnectedClientTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.DisconnectedClient#DisconnectedClient(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientIdentifier, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameController)}
	 * .
	 */
	@Test
	public void testDisconnectedClient() {
		DisconnectedClient dc = new DisconnectedClient(1, null, null);
		assertNotNull(dc);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.DisconnectedClient#getClientID()}
	 * .
	 */
	@Test
	public void testGetClientID() {
		int id = 1;
		DisconnectedClient dc = new DisconnectedClient(id, null, null);

		assertEquals(id, dc.getClientID());
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.DisconnectedClient#getControlledPlayer()}
	 * .
	 */
	@Test
	public void testGetControlledPlayer() {
		Player p = new Player();
		p.setID();
		DisconnectedClient dc = new DisconnectedClient(1, p, null);

		assertEquals(p, dc.getControlledPlayer());
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.DisconnectedClient#getGame()}
	 * .
	 */
	@Test
	public void testGetGame() {
		GameController gc = GameControllerCreator.create(null,
				GameType.ORIGINAL);
		DisconnectedClient dc = new DisconnectedClient(1, null, gc);

		assertEquals(gc, dc.getGame());
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.DisconnectedClient#equals(java.lang.Object)}
	 * .
	 */
	@Test
	public void testEqualsObject() {

		int id1 = 1;
		int id2 = 2;

		DisconnectedClient dc1 = new DisconnectedClient(id1, null, null);
		DisconnectedClient dc2 = new DisconnectedClient(id1, null, null);
		DisconnectedClient dc3 = new DisconnectedClient(id2, null, null);

		assertFalse(dc1 == dc2);
		assertTrue(dc1.equals(dc2));
		assertFalse(dc1.equals(dc3));
	}
}
