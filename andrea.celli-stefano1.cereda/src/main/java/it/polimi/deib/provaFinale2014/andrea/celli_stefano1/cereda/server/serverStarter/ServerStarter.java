package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameController;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameControllerCreator;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameType;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.DisconnectedClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.listOfClientHandler.ListOfClientHandler;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.listOfClientHandler.ListOfClientHandlerSocket;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is an abstract class for common methods in rmi/socket
 * 
 * @author Stefano
 * 
 */
public abstract class ServerStarter {

	/** the number of players to start a game */
	private int maxPlayers;
	/** the type of game (set of rules used) */
	private GameType gameType;
	/** the milliseconds for the timer to start a game with less than maxPlayers */
	private long delay;
	/** timer to start games with less than four players */
	private Timer timer = new Timer();
	/** the timer task to execute at the end of the timers */
	private TimerTask timerTaskStartGame = new TimerTask() {
		public void run() {
			launchGame();
		}
	};
	/** Threads that will handle multiple games */
	private ExecutorService executor = Executors.newCachedThreadPool();
	/** list of waiting clients */
	protected ListOfClientHandler clientHandlers;
	/** list of disconnected clients */
	private ArrayList<DisconnectedClient> disconnectedClients = new ArrayList<DisconnectedClient>();

	/** Constructor of an abstract serverStarter */
	public ServerStarter(int maxPlayers, GameType gameType, long delay) {
		this.maxPlayers = maxPlayers;
		this.gameType = gameType;
		this.delay = delay;
	}

	/**
	 * this method checks if there are at least two players waiting. If it can
	 * it starts a new game, empties the waiting client list and cancel the
	 * timer
	 */
	private void launchGame() {
		if (clientHandlers.size() > 1) {
			// launch the game
			executor.submit(GameControllerCreator.create(clientHandlers,
					gameType));

			// start awaiting for new players
			clientHandlers = new ListOfClientHandlerSocket();
		}

		// if we came here because we had the right number of player the timer
		// will tick again even if we don't have any player waiting
		timer.cancel();
	}

	/** starts the timer if there's at least one player waiting */
	protected void startTimer() {
		if (clientHandlers.size() == 1)
			timer.schedule(timerTaskStartGame, delay);
	}

	/** launch a game if there's the right number of players */
	protected void startGame() {
		if (clientHandlers.size() == maxPlayers) {
			launchGame();
		}
	}

	/**
	 * This method is called when a clients disconnects, it takes note of the
	 * client so it will be possible to reconnect him to the right game
	 */
	public void notifyDisconnection(int identificator) {
		disconnectedClients.add(new DisconnectedClient(identificator, null,
				null));
	}

	/**
	 * Check for previously disconnected clients. If the passed ClientHandler
	 * has the same identificator of a previously disconnected clients this
	 * method sends this client handler to the right game manager
	 * 
	 * @param connected
	 *            The newly connected client to check
	 * @return true if it was a reconnecting client
	 * 
	 * @throws IOException
	 * 
	 */
	protected boolean handlePreviouslyDisconnected(ClientHandler connected) {
		// the equals of a Disconnected client is based on the id
		DisconnectedClient toCheck = new DisconnectedClient(
				connected.getIdentifier(), null, null);

		if (disconnectedClients.contains(toCheck)) {
			int index = disconnectedClients.indexOf(toCheck);
			DisconnectedClient disconnected = disconnectedClients.get(index);

			// get the game controller and player of the disconnected
			GameController gameController = disconnected.getGame();
			Player player = disconnected.getControlledPlayer();

			// notify to the game controller
			gameController.notifyReconnection(player, connected);

			// remove the client from the disconnected list
			disconnectedClients.remove(index);

			return true;
		}
		return false;
	}

	/**
	 * Start accepting requests
	 * 
	 * @throws IOException
	 *             when is not possible to start the server
	 */
	public abstract void start() throws IOException, RemoteException;

	/** Stop the server */
	public abstract void stop();

}
