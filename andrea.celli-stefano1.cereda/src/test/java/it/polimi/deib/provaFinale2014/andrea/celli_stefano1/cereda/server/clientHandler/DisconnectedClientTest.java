/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import static org.junit.Assert.*;

import java.net.InetSocketAddress;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameController;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameControllerCreator;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameType;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class DisconnectedClientTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.DisconnectedClient#DisconnectedClient(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientIdentifier, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player, it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameController)}
	 * .
	 */
	@Test
	public void testDisconnectedClient() {
		DisconnectedClient dc = new DisconnectedClient(null, null, null);
		assertNotNull(dc);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.DisconnectedClient#getClientID()}
	 * .
	 */
	@Test
	public void testGetClientID() {
		ClientIdentifier id = new ClientIdentifier(null, 0);
		DisconnectedClient dc = new DisconnectedClient(id, null, null);

		assertEquals(id, dc.getClientID());
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.DisconnectedClient#getControlledPlayer()}
	 * .
	 */
	@Test
	public void testGetControlledPlayer() {
		Player p = new Player();
		p.setID();
		DisconnectedClient dc = new DisconnectedClient(null, p, null);

		assertEquals(p, dc.getControlledPlayer());
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.DisconnectedClient#getGame()}
	 * .
	 */
	@Test
	public void testGetGame() {
		GameController gc = GameControllerCreator.create(null,
				GameType.ORIGINAL);
		DisconnectedClient dc = new DisconnectedClient(null, null, gc);

		assertEquals(gc, dc.getGame());
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.DisconnectedClient#equals(java.lang.Object)}
	 * .
	 */
	@Test
	public void testEqualsObject() {
		InetSocketAddress addr1 = new InetSocketAddress("localhost", 1000);
		InetSocketAddress addr2 = new InetSocketAddress("localhost", 2000);

		ClientIdentifier id1 = new ClientIdentifier(addr1.getAddress(),
				addr1.getPort());
		ClientIdentifier id2 = new ClientIdentifier(addr2.getAddress(),
				addr2.getPort());

		DisconnectedClient dc1 = new DisconnectedClient(id1, null, null);
		DisconnectedClient dc2 = new DisconnectedClient(id1, null, null);
		DisconnectedClient dc3 = new DisconnectedClient(id2, null, null);

		assertFalse(dc1 == dc2);
		assertTrue(dc1.equals(dc2));
		assertFalse(dc1.equals(dc3));
	}
}
