/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerServer;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ListOfClientHandler;

/**
 * This class takes as input a gametype and returns a gamecontroller basic or
 * advanced. Is like a GameController factory method.
 * 
 * @author Stefano
 * 
 */
public class GameControllerCreator {
	public static GameController create(ListOfClientHandler playerClients,
			GameType gameType) {
		if (gameType.equals(GameType.ORIGINAL)) {
			return new GameController(playerClients);
		} else {
			// TODO return new GameControllerAdvanced(playerClients);
		}
		return null;
	}

	/** Hide the default constructor */
	private GameControllerCreator() {
	}
}
