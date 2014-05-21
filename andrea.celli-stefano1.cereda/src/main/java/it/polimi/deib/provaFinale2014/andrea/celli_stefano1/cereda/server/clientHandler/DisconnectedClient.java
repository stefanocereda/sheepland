/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerServer.GameController;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

/**
 * This class is used to represent a disconnected client, it is represented by
 * the id of the client (ip+port) and a reference to the player controlled and
 * to the game played
 * 
 * @author Stefano
 * 
 */
public class DisconnectedClient {
	/** The id of the client */
	private ClientIdentifier clientID;
	/** The controlled player */
	private Player controlledPlayer;
	/** The game manager associated to the client */
	private GameController game;

	public DisconnectedClient(ClientIdentifier clientID,
			Player controlledPlayer, GameController game) {
		this.clientID = clientID;
		this.controlledPlayer = controlledPlayer;
		this.game = game;
	}

	/**
	 * @return the clientID
	 */
	public ClientIdentifier getClientID() {
		return clientID;
	}

	/**
	 * @return the controlledPlayer
	 */
	public Player getControlledPlayer() {
		return controlledPlayer;
	}

	/**
	 * @return the game
	 */
	public GameController getGame() {
		return game;
	}

	/** Two clients are equals if the have the same id */
	public boolean equals(DisconnectedClient other) {
		if (other.getClientID().equals(clientID)) {
			return true;
		}
		return false;
	}

	/** Override hashCode to match equals */
	public int hashCode() {
		return clientID.hashCode();
	}
}
