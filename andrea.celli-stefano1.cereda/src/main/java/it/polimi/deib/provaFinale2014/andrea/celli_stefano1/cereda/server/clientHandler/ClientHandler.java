package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.Costants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameController;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ClientDisconnectedException;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.ServerStarter;

/**
 * This is an abstract class representing the common proprties of an rmi client
 * handler and a socket client handler
 * 
 * @author Stefano
 * 
 */
public abstract class ClientHandler implements ClientHandlerInterface {
	/** An unique id of this client, zero means still not set */
	protected int id = 0;
	/** A reference to the player controlled by this client */
	protected Player controlledPlayer = null;
	/** A reference to the game this player is playing */
	protected GameController gameController = null;
	/** A reference to the server that created this object */
	private ServerStarter serverStarter = null;
	/** A timer used to ping the client */
	protected Timer timer = new Timer();
	/** the timer task to execute at the end of the timers */
	private TimerTask timerTaskPing = new TimerTask() {
		public void run() {
			try {
				pingTheClient();
			} catch (ClientDisconnectedException e) {
				Logger log = Logger
						.getLogger("server.clientHandler.ClientHandler");
				log.severe("SOCKET ERROR: " + e);
				notifyClientDisconnection();
			}
		}
	};

	/** This constructor sets the server starter and starts the ping thread */
	public ClientHandler(ServerStarter creator) {
		serverStarter = creator;
		timer.scheduleAtFixedRate(timerTaskPing, Costants.PING_TIME,
				Costants.PING_TIME);
	}

	/** Set the game where this client is playing */
	public void setGame(GameController gc) {
		gameController = gc;
	}

	/**
	 * This method returns the player controlled by this client
	 */
	public Player getPlayer() {
		return controlledPlayer;
	}

	/** Set the player controlled */
	public void setPlayer(Player p) {
		controlledPlayer = p;
	}

	/**
	 * This method returns an identifier for this client
	 */
	public int getIdentifier() {
		return id;
	}

	/**
	 * This method set a new identifier
	 */
	public void setIdentifier(int newIdentifier) {
		id = newIdentifier;
	}

	/**
	 * Notify the disconnection of a player to the gameController (so it can
	 * suspend the player) and to the server starter (so it can wait for this
	 * player to reconnect and let it play in the same game).
	 * 
	 * @param gc
	 *            the game controller to notify
	 * @param pc
	 *            the client disconnected
	 */
	public void notifyClientDisconnection() {
		if (id != 0 && gameController != null && controlledPlayer != null) {
			// otherwise it disconnected too soon

			gameController.notifyDisconnection(controlledPlayer);
			serverStarter.notifyDisconnection(id);
		}
	}
}
