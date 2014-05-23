/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.logging.Logger;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler.NetworkhandlerSocket;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.Costants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandlerRMI;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.rmi.RMIConnector;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.rmi.RMICostants;

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
			try {
				launchSocket();
			} catch (IOException e) {
				Logger log = Logger.getLogger("client.ClientMain");
				log.severe("Unable to launch socket connection" + e);
			}
		}

		// launch rmi
		else {
			try {
				launchRMI();
			} catch (RemoteException e) {
				Logger log = Logger.getLogger("client.ClientMain");
				log.severe("Unable to launch rmi connection" + e);
			} catch (NotBoundException e) {
				Logger log = Logger.getLogger("client.ClientMain");
				log.severe("Unable to bound the local client handler" + e);
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

	private static void launchRMI() throws RemoteException, NotBoundException {
		/** get the remote registry */
		Registry registry = LocateRegistry.getRegistry(
				Costants.SERVER_RMI_ADDRESS, Costants.SOCKET_IP_PORT);

		/** Search the server acceptor */
		RMIConnector connector = (RMIConnector) registry
				.lookup(RMICostants.CONNECTOR);

		/** Login with default id=0 */
		Integer myID = connector.connect(0);

		/** Create and export a client handler with the returned id */
		ClientHandlerRMI clientHandler = new ClientHandlerRMI(gameController);
		ClientHandlerRMI stubClientHandler = (ClientHandlerRMI) UnicastRemoteObject
				.exportObject(clientHandler, 0);

		registry.rebind(myID.toString(), stubClientHandler);
	}

	private static void launchSocket() throws IOException {
		/** The server address */
		InetSocketAddress serverAddress = Costants.SERVER_SOCKET_ADDRESS;

		NetworkhandlerSocket socketClient;

		socketClient = new NetworkhandlerSocket(serverAddress, gameController);
		socketClient.start();

	}
}
