package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.Costants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameType;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.ServerStarter;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.ServerStarterRMI;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.ServerStarterSocket;

/**
 * The main ask for RMI/Socket on console and launch the right one
 * (hopefully...) It can be easily modified to start socket and rmi
 * 
 * @author Stefano
 */
public class ServerMainClass {
	/** the max number of players in a game */
	final static int maxPlayers = Costants.MAX_PLAYERS_IN_A_GAME;
	/** the minutes waiting for maxPlayers */
	final static int minutesWaiting = Costants.MINUTES_WAITING_FOR_MAX_PLAYERS;
	/** the type of game (original/extended rules) */
	final static GameType gameType = Costants.DEFAULT_GAME_TYPE;

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
			createSocketServer();
		}

		// Launch an RMI server
		else {
			createRMIServer();
		}

		// launch the server
		try {
			server.start();
		} catch (IOException e) {
			Logger log = Logger.getLogger("server.SeverMainClass");
			log.severe("UNABLE TO START THE SERVER: " + e);
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
		/** stdin */
		Scanner in = new Scanner(System.in);

		do {
			System.out.println("Choose the server type:");
			System.out.println("1 - Socket");
			System.out.println("2 - RMI");
			System.out.println("Insert answer:");
			answer = in.nextLine();
		} while (!answer.equals("1") && !answer.equals("2"));

		in.close();

		if (answer.equals("1")) {
			return 1;
		} else {
			return 2;
		}
	}

	/** Launch a socket server */
	private static void createSocketServer() {
		/** the ip port of the server */
		int port = Costants.SOCKET_IP_PORT;

		// create the server
		server = new ServerStarterSocket(maxPlayers, gameType, minutesWaiting,
				port);
	}

	/** Launch an rmi server */
	private static void createRMIServer() {
		/** The ip port of the registry */
		int port = Costants.REGISTRY_IP_PORT;

		server = new ServerStarterRMI(maxPlayers, gameType, minutesWaiting,
				port);
	}
}
