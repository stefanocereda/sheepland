package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameController;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

/**
 * An exception thrown when the client handler detects a client disconnection
 * 
 * @author Stefano
 * 
 */
public class ClientDisconnectedException extends Exception {
	/** A reference to the game controller where this player was playing */
	private GameController gameController;

	/** A reference to the player controlled */
	private Player player;

	public ClientDisconnectedException(GameController gc, Player pc) {
		gameController = gc;
		player = pc;
	}

	/** Get the game controller where this player was playing */
	public GameController getGameController() {
		return gameController;
	}

	/** Get the player controlled */
	public Player getPlayer() {
		return player;
	}
}
