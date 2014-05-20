package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server;

import java.io.IOException;
import java.util.Scanner;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.Costants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerServer.GameType;

/**
 * The main ask for RMI/Socket on console and launch the right one
 * (hopefully...)
 * 
 * @author Stefano
 * 
 *         TODO rmi server
 */
public class ServerMainClass {
	/** the max number of players in a game */
	final static int maxPlayers = Costants.MAX_PLAYERS_IN_A_GAME;
	/** the minutes waiting for maxPlayers */
	final static int minutesWaiting = Costants.MINUTES_WAITING_FOR_MAX_PLAYERS;
	/** the type of game (original/extended rules) */
	final static GameType gameType = Costants.DEFAULT_GAME_TYPE;
	/** stdin */
	final static Scanner in = new Scanner(System.in);
	/** The server to launch */
	static ServerStarter server = null;

	/**
	 * The main method of the server, asks for RMI/Socket and launches all the
	 * necessary stuff
	 * 
	 * @param args
	 *            Unused
	 */
	public static void main(String[] args) {

		int serverType = chooseServerType();

		// Launch a socket server
		if (serverType == 1) {
			launchSocketServer();
		}

		// Launch an RMI server
		else {
			launchRMIServer();
		}

		// close the input stream
		in.close();

		// launch the server
		try {
			server.start();
		} catch (IOException e) {
			System.err.println("Error starting the server");
			e.printStackTrace();
		}
	}

	/**
	 * Ask on console if the user wants to launch an rmi server or a socket
	 * server
	 * 
	 * @return 1 for socket; 2 for RMI
	 */
	private static int chooseServerType() {
		String answer;

		do {
			System.out.println("Choose the server type:");
			System.out.println("1 - Socket");
			System.out.println("2 - RMI");
			System.out.println("Insert answer:");
			answer = in.nextLine();
		} while (!answer.equals("1") && !answer.equals("2"));

		if (answer.equals("1")) {
			return 1;
		} else {
			return 2;
		}
	}

	/** Launch a socket server */
	private static void launchSocketServer() {
		/** the ip port of the server */
		int port = Costants.SOCKET_IP_PORT;

		// create the server
		server = new SocketServerStarter(port, maxPlayers, minutesWaiting,
				gameType);
	}

	/** Launch an rmi server */
	private static void launchRMIServer() {
		// TODO
	}
}
