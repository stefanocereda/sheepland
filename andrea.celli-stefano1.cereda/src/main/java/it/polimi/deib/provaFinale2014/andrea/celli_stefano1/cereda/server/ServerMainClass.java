package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server;

import java.util.Scanner;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.Costants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.GameType;

//The main asks for RMI/Socket and launches it
public class ServerMainClass {

	public static void main(String[] args) {
		// the max number of players in a game
		final int maxPlayers = Costants.MaxPlayersInGame;
		// the minutes waiting for maxPlayers
		final int minutesWaiting = Costants.MinutesWaitingForMaxPlayers;
		// the type of game (original/extended rules)
		final GameType gameType = Costants.DefaultGameType;

		Scanner in = new Scanner(System.in);
		String answer;

		do {
			System.out.println("Choose the server type:");
			System.out.println("1 - Socket");
			System.out.println("2 - RMI");
			System.out.println("Insert answer:");
			answer = in.nextLine();
		} while (!answer.equals("1") && !answer.equals("2"));

		ServerStarter server = null;

		if (answer.equals("1")) {
			int port;
			do {
				System.out.println("Choose port: ");
				port = in.nextInt();
			} while (port < 0 || port > 65536);
			server = new SocketServerStarter(port, maxPlayers, minutesWaiting,
					gameType);
		}

		else if (answer.equals("2"))
			server = new RMIServerStarter();

		in.close();
		server.start();
	}
}
