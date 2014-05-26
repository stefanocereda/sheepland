package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.Costants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.rmi.RMIConnector;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.rmi.RMICostants;

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
		registry = LocateRegistry.getRegistry(Costants.SERVER_RMI_ADDRESS,
				Costants.REGISTRY_IP_PORT);

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

		// Start to periodically ping the server in order to catch our
		// disconnection
		timer = new Timer();
		timer.scheduleAtFixedRate(timerTaskPong, Costants.PING_TIME,
				Costants.PING_TIME);
	}

	@Override
	protected void checkConnectivity() throws RemoteException, IOException {
		connector.ping();
	}

}
