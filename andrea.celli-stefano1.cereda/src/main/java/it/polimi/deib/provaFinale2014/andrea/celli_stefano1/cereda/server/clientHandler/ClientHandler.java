package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.TimeConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameController;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ClientDisconnectedException;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.ServerStarter;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is an abstract class representing the common properties of an rmi client
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
	/** A reference to the server that will handle the reconnection */
	private ServerStarter serverStarter = null;

	/** A logger */
	private Logger logger = Logger.getLogger(this.getClass().getName());

	/** A timer used to ping the client */
	protected Timer timer = new Timer();
	/** the timer task to execute at the end of the timers */
	protected TimerTask timerTaskPing;

	/** A TimerTask executed periodically by the timer to check connectivity */
	class TimerTaskPing extends TimerTask {
		@Override
		public void run() {
			try {
				pingTheClient();
			} catch (ClientDisconnectedException e) {
				String message = "Error during the periodic ping, the client disconnected";
				logger.log(Level.INFO, message, e);
				notifyClientDisconnection();
			}
		}
	}

	/** This constructor sets the server starter and starts the ping thread */
	public ClientHandler(ServerStarter creator) {
		serverStarter = creator;
		timerTaskPing = new TimerTaskPing();
		timer.scheduleAtFixedRate(timerTaskPing, TimeConstants.PING_TIME,
				TimeConstants.PING_TIME);
	}

	/**
	 * Notify the disconnection of a player to the gameController (so it can
	 * suspend the player) and to the server starter (so it can wait for this
	 * player to reconnect and let it play in the same game).
	 */
	public void notifyClientDisconnection() {
		if (id != 0 && gameController != null && controlledPlayer != null) {
			// otherwise it disconnected too soon
			timerTaskPing.cancel();
			gameController.notifyDisconnection(controlledPlayer);
			serverStarter.notifyDisconnection(id);
		}
	}

	/** Set the game where this client is playing */
	public void setGame(GameController gc) {
		gameController = gc;
	}

	/** @return the player controlled by this client */
	public Player getPlayer() {
		return controlledPlayer;
	}

	/** Set the player controlled */
	public void setPlayer(Player p) {
		controlledPlayer = p;
	}

	/** @return the identifier of this client */
	public int getIdentifier() {
		return id;
	}

	/** This method set a new identifier */
	public void setIdentifier(int newIdentifier) {
		id = newIdentifier;
	}
}
