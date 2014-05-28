package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameController;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

/**
 * An exception thrown when the client handler detects that his client
 * disconnected. This exception contains a reference to the game controller and
 * to the player of the disconnected client.
 * 
 * @author Stefano
 * 
 */
public class ClientDisconnectedException extends Exception {
	/** A reference to the game controller where this player was playing */
	private GameController gameController;

	/** A reference to the player controlled */
	private Player player;

	/** The original exception */
	private Exception originalException;

	/**
	 * The constructor sets the parameter
	 * 
	 * @param gc
	 *            A reference to the game controller where this player was
	 *            playing
	 * @param pc
	 *            A reference to the player controlled
	 * @param e
	 *            The original exception
	 */
	public ClientDisconnectedException(GameController gc, Player pc, Exception e) {
		gameController = gc;
		player = pc;
		originalException = e;
	}

	/** @return the game controller where this player was playing */
	public GameController getGameController() {
		return gameController;
	}

	/** @return the player controlled */
	public Player getPlayer() {
		return player;
	}

	/** @return the original exception */
	public Exception getOriginalException() {
		return originalException;
	}
}
