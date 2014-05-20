package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerServer.GameControllerCreator;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerServer.GameType;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ListOfSocketClientHandler;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.SocketClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
			try {
				Socket socket = serverSocket.accept();
				clientHandlers.add(new SocketClientHandler(socket));

				// if it's the first player waiting start the timer
				if (clientHandlers.size() == 1)
					timer.schedule(timerTaskStartGame, delay);

				// if we have six players awaiting we start the game
				if (clientHandlers.size() == maxPlayers) {
					launchGame();
				}

			} catch (IOException e) {
				System.err.println(e.getMessage());
				break;
			}
		}

		executor.shutdown();
		serverSocket.close();
	}

	/**
	 * this method checks if there is at least two players waiting. If it can it
	 * starts a new game, empties the waiting client list and cancel the timer
	 */
	private void launchGame() {
		if (clientHandlers.size() > 1) {
			executor.submit(GameControllerCreator.create(clientHandlers,
					gameType));

			clientHandlers = new ListOfSocketClientHandler();
		}

		timer.cancel();
	}
}
