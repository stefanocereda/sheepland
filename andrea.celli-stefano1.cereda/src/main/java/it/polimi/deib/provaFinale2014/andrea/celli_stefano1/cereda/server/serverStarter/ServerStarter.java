package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameController;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameControllerCreator;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameType;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.DisconnectedClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class launches a server for socket connections and a server for rmi
 * connections. Those server accept connections and create clientHandlers which
 * are give back to this class. Here we check if those client are previously
 * disconnected client and we keep a list of waiting client. When is possible we
 * start a new game on a separate thread. This class implements Runnable, so it
 * will be possible to launch multiple servers with different game settings
 * (obviously they will have to be on different ports). All the methods (except
 * start and stop) are synchronized, in this way we're sure that can't happen
 * strange thing like: the server socket and the rmi add a player in the same
 * time and we try to launch a game two times, the second without having
 * players.
 * 
 * @author Stefano
 * 
 */
public class ServerStarter implements Runnable {
	/** the number of players to start a game */
	private int maxPlayers;
	/** the type of game (set of rules used) */
	private GameType gameType;
	/** the milliseconds for the timer to start a game with less than maxPlayers */
	private long timeWaitingForMaxPlayers;

	/** the ip port for socket server */
	private int socketPort;
	/** the ip port for the rmi registry */
	private int rmiPort;

	/** The socket server */
	ServerStarterSocket socketServer;
	/** The rmi server */
	ServerStarterRMI rmiServer;

	/** timer to start games with less than four players */
	private Timer timer = new Timer();
	/** the timer task to execute at the end of the timer to launch a game */
	private TimerTask timerTaskStartGame;

	/**
	 * A TimerTask that will be executed periodically by the timer to check if
	 * we can start a game
	 */
	class TimerTaskStartGame extends TimerTask {
		@Override
		public void run() {
			launchGame();
		}
	}

	/** Threads that will handle multiple games */
	private ExecutorService executor = Executors.newCachedThreadPool();

	/** A list of waiting clients */
	private List<ClientHandler> waitingClients = new ArrayList<ClientHandler>();
	/** A list of disconnected clients */
	private List<DisconnectedClient> disconnectedClients = new ArrayList<DisconnectedClient>();

	/**
	 * The constructor of a server starter takes the game parameter and the
	 * network ports. It doesn't start the server
	 * 
	 * @param maxPlayers
	 *            The max number of players in a game
	 * @param gameType
	 *            The set of rules to use
	 * @param delay
	 *            The time waiting to have the right number of players before
	 *            start with less
	 * @param socketPort
	 *            The IP port for the socket server
	 * @param rmiPort
	 *            The IP port for the rmi registry (must be different from
	 *            socket)
	 */
	public ServerStarter(int maxPlayers, GameType gameType, long delay,
			int socketPort, int rmiPort) {
		this.maxPlayers = maxPlayers;
		this.gameType = gameType;
		this.timeWaitingForMaxPlayers = delay;
		this.socketPort = socketPort;
		this.rmiPort = rmiPort;
	}

	/** This method actually starts the rmi and socket servers */
	public void run() {
		socketServer = new ServerStarterSocket(socketPort, this);
		rmiServer = new ServerStarterRMI(rmiPort, this);

		(new Thread(socketServer)).start();
		(new Thread(rmiServer)).start();
	}

	/** This method close the servers */
	public void stop() {
		socketServer.stop();
		rmiServer.stop();
	}

	/**
	 * This method is called by the rmi and socket servers in order to add a new
	 * clientHandler to the list of waiting clients
	 */
	public synchronized void addClient(ClientHandler newClient) {
		// if it's a previously disconnected client give it to the right method
		if (isPreviouslyDisconnected(newClient)) {
			handlePreviouslyDisconnected(newClient);
			return;
		}

		// otherwise add it
		waitingClients.add(newClient);
		// if it's the first client waiting start a timer
		startTimer();
		// if we have the right number of players start a game
		startGame();
	}

	/**
	 * This method is called when a clients disconnects, it takes note of the
	 * client so it will be possible to reconnect him to the right game
	 */
	public synchronized void notifyDisconnection(int identificator) {
		disconnectedClients.add(new DisconnectedClient(identificator, null,
				null));
	}

	/** starts the timer if there's one player waiting */
	private synchronized void startTimer() {
		if (waitingClients.size() == 1) {
			timerTaskStartGame = new TimerTaskStartGame();
			timer.schedule(timerTaskStartGame, timeWaitingForMaxPlayers);
		}
	}

	/** launch a game if there's the right number of players */
	private synchronized void startGame() {
		if (waitingClients.size() == maxPlayers) {
			launchGame();
		}
	}

	/**
	 * This method checks if there are at least two players waiting (it can be
	 * called by the automatic timer when we have only one client waiting). If
	 * it can it starts a new game, empties the waiting client list and cancel
	 * the timer
	 */
	private synchronized void launchGame() {
		if (waitingClients.size() > 1) {
			// launch the game
			executor.submit(GameControllerCreator.create(waitingClients,
					gameType));

			// start awaiting for new players
			waitingClients = new ArrayList<ClientHandler>();

			// stop the timer for this game
			timerTaskStartGame.cancel();
		}
	}

	/** Check if the passed client is a previously disconnected client */
	private synchronized boolean isPreviouslyDisconnected(
			ClientHandler newClient) {
		// Create a fake disconnected client with the same id of the new client,
		// the equals of a DisconnectedClient is based on that
		DisconnectedClient toCheck = new DisconnectedClient(
				newClient.getIdentifier(), null, null);

		return waitingClients.contains(toCheck);
	}

	/**
	 * This method is called when we find a reconnecting client. We search for
	 * the previosly game and player and notifies to the game controller the
	 * reconnection
	 */
	private synchronized void handlePreviouslyDisconnected(
			ClientHandler reconnectedClient) {
		// create a fake disconnected client
		DisconnectedClient toSearch = new DisconnectedClient(
				reconnectedClient.getIdentifier(), null, null);
		// search the real disconnected client
		int index = disconnectedClients.indexOf(toSearch);
		DisconnectedClient disconnected = disconnectedClients.get(index);

		// get the game controller and player of the disconnected
		GameController gameController = disconnected.getGame();
		Player player = disconnected.getControlledPlayer();

		// notify to the game controller
		gameController.notifyReconnection(player, reconnectedClient);

		// remove the client from the disconnected list
		disconnectedClients.remove(index);
	}
}
