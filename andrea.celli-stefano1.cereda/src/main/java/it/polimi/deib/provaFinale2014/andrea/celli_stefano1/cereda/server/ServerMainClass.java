package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server;

import java.util.Scanner;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.Costants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.GameType;

/**
 * The main ask for RMI/Socket on console and launch the right one
 * (hopefully...)
 * 
 * @author Stefano
 * 
 *         TODO rmi server
 */
public class ServerMainClass {

	public static void main(String[] args) {
		/** the max number of players in a game */
		final int maxPlayers = Costants.MaxPlayersInGame;
		/** the minutes waiting for maxPlayers */
		final int minutesWaiting = Costants.MinutesWaitingForMaxPlayers;
		/** the type of game (original/extended rules) */
		final GameType gameType = Costants.DefaultGameType;
		/** stdin */
		Scanner in = new Scanner(System.in);
		String answer;

		// ask for rmi/socket
		do {
			System.out.println("Choose the server type:");
			System.out.println("1 - Socket");
			System.out.println("2 - RMI");
			System.out.println("Insert answer:");
			answer = in.nextLine();
		} while (!answer.equals("1") && !answer.equals("2"));

		/** The server to launch */
		ServerStarter server = null;

		// Launch a socket server
		if (answer.equals("1")) {
			/** the ip port of the server */
			int port = Costants.SocketIpPort;

			// create the server
			server = new SocketServerStarter(port, maxPlayers, minutesWaiting,
					gameType);
		}

		// Launch an RMI server
		else if (answer.equals("2"))
			// TODO server = new RMIServerStarter();

			// close the input stream and launch the server
			in.close();
		server.start();
	}
}
