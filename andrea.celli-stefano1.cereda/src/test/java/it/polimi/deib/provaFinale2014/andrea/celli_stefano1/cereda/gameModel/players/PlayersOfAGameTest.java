/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Stefano
 * 
 */
public class PlayersOfAGameTest {

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.PlayersOfAGame#PlayersOfAGame(int)}
	 * .
	 */
	@Test
	public void testPlayersOfAGame() {
		PlayersOfAGame poag = new PlayersOfAGame(6);
		poag.setID();
		assertNotNull(poag);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.PlayersOfAGame#getPlayers()}
	 * .
	 */
	@Test
	public void testGetPlayers() {
		PlayersOfAGame poag = new PlayersOfAGame(6);
		poag.setID();
		Player[] players = poag.getPlayers();

		assertNotNull(players);
		assertEquals(players.length, 6);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.PlayersOfAGame#addPlayerToPlayersOfAGame(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player)}
	 * .
	 */
	@Test
	public void testAddPlayerToPlayersOfAGame() {
		PlayersOfAGame poag = new PlayersOfAGame(6);
		poag.setID();
		Player player = new Player();
		player.setID();
		poag.addPlayerToPlayersOfAGame(player);
		Player[] players = poag.getPlayers();

		boolean addeddCorrectly = false;
		for (Player p : players) {
			if (p.equals(player)) {
				addeddCorrectly = true;
				break;
			}
		}

		assertTrue(addeddCorrectly);
	}

	/**
	 * Test method for
	 * {@link it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.PlayersOfAGame#isAlreadyThere(it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player)}
	 * .
	 */
	@Test
	public void testIsAlreadyThere() {
		PlayersOfAGame poag = new PlayersOfAGame(6);
		poag.setID();
		Player player1 = new Player();
		player1.setID();
		Player player2 = new Player();
		player2.setID();
		poag.addPlayerToPlayersOfAGame(player1);

		assertTrue(poag.isAlreadyThere(player1));
		assertFalse(poag.isAlreadyThere(player2));
	}

}
