/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.logging.Logger;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler.NetworkhandlerSocket;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.Costants;

/**
 * The main class of the client, asks for RMI/socket and creates a network
 * manager linked to a client game controller
 * 
 * @author Stefano
 * 
 */
public class ClientMain {
	/** A Client game controller */
	static GameControllerClient gameController = new GameControllerClient();

	public static void main(String[] args) {
		// choose a communication model
		int clientType = chooseType();

		// launch a socket client
		if (clientType == 1) {
			launchSocket();
		}

		// launch rmi
		else {
			launchRMI();
		}
	}

	/**
	 * Ask the user to choose between socket and rmi
	 * 
	 * @return 1 for socket; 2 for RMI
	 */
	private static int chooseType() {
		String answer;
		Scanner in = new Scanner(System.in);

		do {
			System.out.println("Choose the client type:");
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

	private static void launchRMI() {
		// TODO Auto-generated method stub

	}

	private static void launchSocket() {
		/** The server address */
		InetSocketAddress serverAddress = Costants.SERVER_SOCKET_ADDRESS;

		NetworkhandlerSocket socketClient;

		try {
			socketClient = new NetworkhandlerSocket(serverAddress,
					gameController);
			socketClient.start();
		} catch (IOException e) {
			Logger log = Logger.getLogger("client.ClientMain");
			log.severe(e.toString());
		}
	}
}
