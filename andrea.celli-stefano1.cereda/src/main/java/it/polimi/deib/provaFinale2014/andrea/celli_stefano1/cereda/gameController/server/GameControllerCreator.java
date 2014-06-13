/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;

import java.util.List;

/**
 * This class takes as input a gameType and returns a gameController basic or
 * advanced. Is like a GameController factory method.
 * 
 * @author Stefano
 * 
 */
public class GameControllerCreator {
	/** Hide the default constructor */
	private GameControllerCreator() {
	}

	/**
	 * Returns a GameController or a GameControllerAdvanced based on the
	 * gameType passed
	 * 
	 * @param playerClients
	 *            The list of client handlers to be passed to the game
	 *            controller
	 * @param gameType
	 *            the set of rules you want to use
	 * @return a game controller for the right set of rules
	 */
	public static GameController create(List<ClientHandler> playerClients,
			GameType gameType) {
		if (gameType.equals(GameType.ORIGINAL)) {
			return new GameController(playerClients);
		} else {
			return new GameControllerExtended(playerClients);
		}
	}

	/**
	 * Returns a GameController or a GameControllerAdvanced based on the
	 * gameType passed, but with only two players connected
	 * 
	 * @param playerClients
	 *            The list of client handlers to be passed to the game
	 *            controller
	 * @param gameType
	 *            the set of rules you want to use
	 * @return a game controller for the right set of rules
	 */
	public static GameController createTwoPlayers(
			List<ClientHandler> playerClients, GameType gameType) {
		if (gameType.equals(GameType.ORIGINAL)) {
			return new GameControllerTwoPlayers(playerClients);
		} else {
			return new GameControllerExtendedTwoPlayers(playerClients);
		}
	}
}
