/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameController;
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

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((clientID == null) ? 0 : clientID.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof DisconnectedClient)) {
			return false;
		}
		DisconnectedClient other = (DisconnectedClient) obj;
		if (clientID == null) {
			if (other.clientID != null) {
				return false;
			}
		} else if (!clientID.equals(other.clientID)) {
			return false;
		}
		return true;
	}

	
}
