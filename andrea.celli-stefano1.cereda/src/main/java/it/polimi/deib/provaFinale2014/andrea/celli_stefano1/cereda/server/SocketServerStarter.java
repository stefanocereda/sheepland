package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerServer.GameController;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerServer.GameControllerCreator;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerServer.GameType;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientIdentifier;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ListOfSocketClientHandler;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.SocketClientHandler;

import java.util.List;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The socket version of a server
 * 
 * @author Stefano
 */
public class SocketServerStarter implements ServerStarter {

	/** the ip port of the server */
	private int port;
	/** the number of players to start a game */
	private int maxPlayers;
	/** the milliseconds for the timer to start a game with less than maxPlayers */
	private long delay;
	/** the type of game (set of rules used) */
	private GameType gameType;
	/** Threads that will handle multiple games */
	private ExecutorService executor = Executors.newCachedThreadPool();
	/** the socket for income connections */
	private ServerSocket serverSocket = null;
	/** list of waiting clients */
	private ListOfSocketClientHandler clientHandlers = new ListOfSocketClientHandler();
	/** timer to start games with less than six players */
	private Timer timer = new Timer();
	/** the timer task to execute at the end of the timers */
	private TimerTask timerTaskStartGame = new TimerTask() {
		public void run() {
			launchGame();
		}
	};
	/** A list of disconnected clients */
	private List<DisconnectedClient> disconnectedClients = new ArrayList<DisconnectedClient>();

	/**
	 * Socket server constructor
	 * 
	 * @param port
	 *            ip port of the server
	 * @param maxPlayers
	 *            number of max players in a game
	 * @param minutesWaiting
	 *            minutes waiting for maxPlayers
	 * @param gameType
	 *            set of rules to use
	 */
	public SocketServerStarter(int port, int maxPlayers, int minutesWaiting,
			GameType gameType) {
		this.port = port;
		this.maxPlayers = maxPlayers;
		this.delay = minutesWaiting * 60 * 1000;
		this.gameType = gameType;
	}

	/**
	 * Start the socket server
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException {

		// opens the socket for incoming connections
		serverSocket = new ServerSocket(port);

		// wait for connections and create the clientHandlers
		while (true) {
			Socket socket = serverSocket.accept();

			// if it's a previously disconnected client we don't need to handle
			// it here
			if (isPreviouslyDisconnected(socket))
				break;

			clientHandlers.add(new SocketClientHandler(socket, this));
			// if it's the first player waiting start the timer
			if (clientHandlers.size() == 1)
				timer.schedule(timerTaskStartGame, delay);

			// if we have six players awaiting we start the game
			if (clientHandlers.size() == maxPlayers) {
				launchGame();
			}
		}
	}

	/**
	 * this method checks if there are at least two players waiting. If it can
	 * it starts a new game, empties the waiting client list and cancel the
	 * timer
	 */
	private void launchGame() {
		if (clientHandlers.size() > 1) {
			executor.submit(GameControllerCreator.create(clientHandlers,
					gameType));

			clientHandlers = new ListOfSocketClientHandler();
		}

		timer.cancel();
	}

	/**
	 * This method is called when a clients disconnects, it takes note of the
	 * client so it will be possible to reconnect him to the right game
	 */
	public void notifyDisconnection(ClientIdentifier disconnectedId,
			GameController gc, Player playerControlled) {

		DisconnectedClient disconnected = new DisconnectedClient(
				disconnectedId, playerControlled, gc);
		disconnectedClients.add(disconnected);
	}

	/**
	 * Check for previously disconnected clients. If the passed socket has the
	 * same ip+port of a previously disconnected clients this method sends this
	 * socket to the right game manager
	 * @throws IOException 
	 * 
	 */
	private boolean isPreviouslyDisconnected(Socket connected) throws IOException {
		// get the id
		ClientIdentifier id = new ClientIdentifier(connected.getInetAddress(),
				connected.getPort());

		// get a fake disconnected client to check in the arraylist
		DisconnectedClient newClient = new DisconnectedClient(id, null, null);

		// the .contains should be based on client identifier
		if (disconnectedClients.contains(newClient)) {
			int index = disconnectedClients.indexOf(newClient);
			DisconnectedClient disconnected = disconnectedClients.get(index);
			// get the game controller and player of the disconnected
			GameController gameController = disconnected.getGame();
			Player player = disconnected.getControlledPlayer();
			// create a new client handler
			ClientHandler newClientHandler = new SocketClientHandler(connected,
					this);
			// notify to the game controller
			gameController.notifyReconnection(player, newClientHandler);

			disconnectedClients.remove(index);

			return true;
		}

		return false;
	}
}
