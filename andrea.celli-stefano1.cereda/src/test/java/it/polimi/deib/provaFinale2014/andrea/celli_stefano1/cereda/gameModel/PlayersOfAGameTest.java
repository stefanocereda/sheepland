/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

import static org.junit.Assert.*;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.PlayersOfAGame;

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
		Player player = new Player();
		poag.addPlayerToPlayersOfAGame(player);
		Player[] players = poag.getPlayers();

		boolean addeddCorrectly = false;
		for (Player p : players) {
			if (p == player) {
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
		Player player1 = new Player();
		Player player2 = new Player();
		poag.addPlayerToPlayersOfAGame(player1);

		assertTrue(poag.isAlreadyThere(player1));
		assertFalse(poag.isAlreadyThere(player2));
	}

}
