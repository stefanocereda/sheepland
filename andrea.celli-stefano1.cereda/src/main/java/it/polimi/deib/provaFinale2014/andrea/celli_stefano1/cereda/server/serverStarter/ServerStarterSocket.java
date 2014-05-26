package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameType;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandlerSocket;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.listOfClientHandler.ListOfClientHandlerSocket;

/**
 * The socket version of server starter
 * 
 * @author Stefano
 * 
 */
public class ServerStarterSocket extends ServerStarter {
	/** The ip port of the server */
	private int port;
	/** the server socket for income connections */
	private ServerSocket serverSocket = null;

	/**
	 * Creates a socket server starter
	 * 
	 * @param maxPlayers
	 *            number of max players in a game
	 * @param minutesWaiting
	 *            minutes waiting for maxPlayers
	 * @param gameType
	 *            set of rules to use
	 * @param port
	 *            ip port of the server
	 */
	public ServerStarterSocket(int maxPlayers, GameType gameType, long delay,
			int port) {
		super(maxPlayers, gameType, delay);
		this.port = port;
		clientHandlers = new ListOfClientHandlerSocket();
	}

	@Override
	public void start() throws IOException {
		// opens the socket for incoming connections
		serverSocket = new ServerSocket(port);

		// wait for connections and create the client handlers
		while (true) {
			Socket acceptedSocket = serverSocket.accept();
			ClientHandlerSocket acceptedHandler = new ClientHandlerSocket(this,
					acceptedSocket);

			// if it's a previously disconnected client we don't need to handle
			// it here
			if (handlePreviouslyDisconnected(acceptedHandler)) {
				break;
			}

			// otherwise add the clientHandler to the waiting list
			clientHandlers.add(acceptedHandler);

			// try to start the timer
			startTimer();

			// if we have the right number of players awaiting we start the game
			startGame();

		}

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
