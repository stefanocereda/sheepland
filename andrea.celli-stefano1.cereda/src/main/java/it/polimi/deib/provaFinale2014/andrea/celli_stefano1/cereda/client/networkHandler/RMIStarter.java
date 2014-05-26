/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.Costants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.rmi.RMIConnector;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.rmi.RMICostants;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * @author Stefano
 * 
 */
public class RMIStarter {
	/** The rmi registry */
	private static Registry registry;
	/** The remote object */
	private static RMIConnector connector;
	/** The client identificator */
	private static Integer myID = 0;
	/** The game controller */
	private static GameControllerClient gameControllerClient;
	/** A timer to periodically ping the server */
	private static Timer timer = new Timer();
	private static TimerTask timerTaskPing = new TimerTask() {
		public void run() {
			try {
				connector.ping();
			} catch (RemoteException e) {
				Logger logger = Logger
						.getLogger("client.networkHandler.RMIStarter");
				logger.severe("we're disconnected" + e);
				timer.cancel();
				reconnect();
			}
		}
	};

	public static void startRMI(GameControllerClient gameController)
			throws RemoteException, NotBoundException {
		gameControllerClient = gameController;

		// get the remote registry
		registry = LocateRegistry.getRegistry(Costants.SERVER_RMI_ADDRESS,
				Costants.REGISTRY_IP_PORT);

		// Search the server acceptor
		connector = (RMIConnector) registry.lookup(RMICostants.CONNECTOR);
	}

	private static void connect() throws RemoteException {
		// Login with the previous id (0 the first time)
		myID = connector.connect(myID);

		// Create and export a network handler with the returned id
		NetworkHandlerInterface networkHandler = new NetworkHandlerRMI(
				gameControllerClient);
		NetworkHandlerInterface stubNetworkHandler = (NetworkHandlerInterface) UnicastRemoteObject
				.exportObject(networkHandler, 0);

		registry.rebind(myID.toString(), stubNetworkHandler);

		// Start to periodically ping the server in order to catch our
		// disconnection
		timer.scheduleAtFixedRate(timerTaskPing, Costants.PING_TIME,
				Costants.PING_TIME);
	}

	private static void reconnect() {
		try {
			connect();
		} catch (RemoteException e) {
			Logger logger = Logger
					.getLogger("client.networkHandler.RMIStarter");
			logger.severe("Unable to reconnect " + e);
			reconnect();
		}
	}
}
