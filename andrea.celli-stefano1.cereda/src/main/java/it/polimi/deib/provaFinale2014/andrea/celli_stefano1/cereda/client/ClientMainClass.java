/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.Interface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.InterfaceCreator;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler.NetworkHandlerRMI;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler.NetworkHandlerSocket;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.DefaultConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.NetworkConstants;

/**
 * The main class of the client, asks for RMI/socket and creates a network
 * manager linked to a client game controller
 * 
 * @author Stefano
 * 
 */
public class ClientMainClass {
	/** A user interface */
	static Interface userInterface = InterfaceCreator
			.create(DefaultConstants.DEFAULT_INTERFACE);
	/** A Client game controller */
	static GameControllerClient gameController = new GameControllerClient(
			userInterface);

	/** A logger */
	static Logger logger = Logger.getLogger("client.ClientMainClass");

	/** The main method of a client */
	public static void main(String[] args) {
		// choose a communication model
		int clientType = chooseType();

		// launch a socket client
		if (clientType == 1) {
			try {
				launchSocket();
			} catch (IOException e) {
				String message = "Unable to start Socket connection";
				logger.log(Level.SEVERE, message, e);
			}
		}

		// launch rmi
		else {
			try {
				launchRMI();
			} catch (RemoteException e) {
				String message = "Unable to start rmi connection";
				logger.log(Level.SEVERE, message, e);
			} catch (NotBoundException e) {
				String message = "Unable to start rmi connection";
				logger.log(Level.SEVERE, message, e);
			}
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

	/**
	 * This method launches the rmi version of a client. This method is public
	 * only for testing puropse, we have a test that calls this method (we can't
	 * call the main because we can't give any input)
	 */
	public static void launchRMI() throws RemoteException, NotBoundException {
		NetworkHandlerRMI rmiClient = new NetworkHandlerRMI(gameController);
		rmiClient.connect();
	}

	/**
	 * This method launches the socket version of a client, it connects to the
	 * server and creates a network handler.This method is public only for
	 * testing puropse, we have a test that calls this method (we can't call the
	 * main because we can't give any input)
	 */
	public static void launchSocket() throws IOException {
		/** The server address */
		InetSocketAddress serverAddress = NetworkConstants.SERVER_SOCKET_ADDRESS;

		NetworkHandlerSocket socketClient;

		socketClient = new NetworkHandlerSocket(serverAddress, gameController);
		socketClient.start();
	}
}
