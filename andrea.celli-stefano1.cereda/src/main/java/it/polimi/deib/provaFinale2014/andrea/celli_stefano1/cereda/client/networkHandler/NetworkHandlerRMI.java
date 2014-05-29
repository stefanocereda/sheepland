package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.NetworkConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.RMICostants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.rmi.RMIConnector;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * The rmi version of a network handler. It connects to the server, receives an
 * id and publish an object of type RMIInterface with the returned id as name
 * 
 * @author Stefano
 * 
 */
public class NetworkHandlerRMI extends NetworkHandler {
	/** The rmi registry */
	private static Registry registry;
	/** The remote connector object */
	private static RMIConnector connector;

	/**
	 * the constructor of an rmi network handler needs to receive a reference to
	 * the client's game controller
	 */
	public NetworkHandlerRMI(GameControllerClient gameController)
			throws RemoteException, NotBoundException {
		super(gameController);

		// get the remote registry
		registry = LocateRegistry.getRegistry(
				NetworkConstants.SERVER_RMI_ADDRESS,
				NetworkConstants.REGISTRY_IP_PORT);

		// Search the server acceptor
		connector = (RMIConnector) registry.lookup(RMICostants.CONNECTOR);
	}

	/**
	 * This method connects to the server and creates a network handler and
	 * starts pinging the server
	 */
	public void connect() throws RemoteException {
		// Login with the previous id (0 the first time)
		myId = connector.connect(myId);

		// Create and export a network handler with the returned id
		RMIInterface rmiHandler = new RMIImpl(controller);
		RMIInterface stubRMIHandler = (RMIInterface) UnicastRemoteObject
				.exportObject(rmiHandler, 0);

		registry.rebind(myId.toString(), stubRMIHandler);
	}
}
