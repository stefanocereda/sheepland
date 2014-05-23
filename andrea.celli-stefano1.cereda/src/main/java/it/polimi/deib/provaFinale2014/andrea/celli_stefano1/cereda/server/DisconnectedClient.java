/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameController;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

/**
 * This class is used to represent a disconnected client, it is represented by
 * the id of the client and a reference to the player controlled and to the game
 * played
 * 
 * @author Stefano
 * 
 */
public class DisconnectedClient {
	/** The id of the client */
	private int clientID;
	/** The controlled player */
	private Player controlledPlayer;
	/** The game manager associated to the client */
	private GameController game;

	public DisconnectedClient(int clientID, Player controlledPlayer,
			GameController game) {
		this.clientID = clientID;
		this.controlledPlayer = controlledPlayer;
		this.game = game;
	}

	/**
	 * @return the clientID
	 */
	public int getClientID() {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + clientID;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		if (clientID != other.clientID) {
			return false;
		}
		return true;
	}

}
