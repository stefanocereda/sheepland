/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.Interface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.InterfaceCreator;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.TypeOfInterface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler.NetworkHandlerRMI;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler.NetworkHandlerSocket;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.NetworkConstants;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main class of the client, asks for RMI/socket and creates a network
 * manager linked to a client game controller
 * 
 * @author Stefano
 * 
 */
public class ClientMainClass {
	/** A logger */
	static Logger logger = Logger.getLogger("client.ClientMainClass");
	/** The console scanner */
	static Scanner in = new Scanner(System.in);

	/**
	 * The main method of a client
	 * 
	 * @param args
	 *            You can use this parameter to describe the kind of client
	 *            wanted. You can insert "socket" or "rmi" for the connections
	 *            and "console", "gui" or "fake" for the interfaces
	 */
	public static void main(String[] args) {
		// parse param
		TypeOfInterface userInterface = null;
		int network = 0;
		int token = 0;

		for (String arg : args) {
			if (arg.equals("socket")) {
				network = 1;
			} else if (arg.equals("rmi")) {
				network = 2;
			} else if (arg.equals("console")) {
				userInterface = TypeOfInterface.CONSOLE;
			} else if (arg.equals("gui")) {
				userInterface = TypeOfInterface.GUI;
			} else if (arg.equals("fake")) {
				userInterface = TypeOfInterface.FAKE;
			}
		}

		System.out
				.println("Insert a previous token if you have one, 0 otherwise: ");
		token = Integer.parseInt(in.nextLine());

		if (userInterface == null) {
			userInterface = askUserInterface();
		}
		if (network == 0) {
			network = askNetwork();
		}

		// create the interface
		Interface ux = InterfaceCreator.create(userInterface);

		// create the game controller
		GameControllerClient gameController = new GameControllerClient(ux);

		// launch the network handler
		if (network == 1) {
			try {
				launchSocket(gameController, token);
			} catch (IOException e) {
				String message = "Unable to start Socket connection";
				logger.log(Level.SEVERE, message, e);
			}
		}

		// launch rmi
		else {
			try {
				launchRMI(gameController, token);
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
	 * Ask the user to choose the kind of interface to launch
	 * 
	 * @return The TypeOfInterface selected
	 */
	private static TypeOfInterface askUserInterface() {
		String answer;

		do {
			System.out.println("Choose the interface type:");
			System.out.println("1 - Console");
			System.out.println("2 - Gui");
			System.out.println("Insert answer:");
			answer = in.nextLine();
		} while (!answer.equals("1") && !answer.equals("2"));

		if (answer.equals("1")) {
			return TypeOfInterface.CONSOLE;
		} else {
			return TypeOfInterface.GUI;
		}
	}

	/**
	 * Ask the user to choose between socket and rmi
	 * 
	 * @return 1 for socket; 2 for RMI
	 */
	private static int askNetwork() {
		String answer;

		do {
			System.out.println("Choose the network type:");
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

	/**
	 * This method launches the rmi version of a client.
	 */
	private static void launchRMI(GameControllerClient gcc, int token)
			throws RemoteException, NotBoundException {
		NetworkHandlerRMI rmiClient = new NetworkHandlerRMI(gcc, token);
		rmiClient.connect();
	}

	/**
	 * This method launches the socket version of a client, it connects to the
	 * server and creates a network handler.
	 */
	private static void launchSocket(GameControllerClient gcc, int token)
			throws IOException {
		/** The server address */
		InetSocketAddress serverAddress = NetworkConstants.SERVER_SOCKET_ADDRESS;

		NetworkHandlerSocket socketClient;

		socketClient = new NetworkHandlerSocket(serverAddress, gcc, token);
		socketClient.start();
	}
}
