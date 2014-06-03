package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.NetworkConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.RMICostants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.TimeConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.rmi.RMIConnector;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

/**
 * The rmi version of a network handler. It connects to the server, receives an
 * id and publish an object of type RMIInterface with the returned id as name.
 * We also have a timer for pinging the server in order to be able to detect our
 * disconnection
 * 
 * 
 * @author Stefano
 * 
 */
public class NetworkHandlerRMI extends NetworkHandler {
	/** The rmi registry */
	private static Registry registry;
	/** The remote connector object */
	private static RMIConnector connector;

	/** The timer used to ping the server */
	private Timer timerPing = new Timer();
	private TimerTask timerTaskPing;

	/** A TimerTask executed periodically by the timer to check connectivity */
	class TimerTaskPing extends TimerTask {
		@Override
		public void run() {
			try {
				pingTheServer();
			} catch (RemoteException e) {
				String message = "Error during the periodic ping, we are disconnected";
				logger.log(Level.INFO, message, e);
				controller.notifyDisconnection();
				timerTaskPing.cancel();
				reconnect();
			}
		}
	}

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
	 * This method pings the server
	 * 
	 * @throws RemoteException
	 */
	public void pingTheServer() throws RemoteException {
		connector.ping();
	}

	/**
	 * This method connects to the server and creates a network handler and
	 * starts pinging the server
	 */
	@Override
	public void connect() throws RemoteException {
		// Login with the previous id (0 the first time)
		myId = connector.connect(myId);

		// Create and export a network handler with the returned id
		RMIInterface rmiHandler = new RMIImpl(controller);
		RMIInterface stubRMIHandler = (RMIInterface) UnicastRemoteObject
				.exportObject(rmiHandler, 0);

		registry.rebind(myId.toString(), stubRMIHandler);

		// start pinging the server
		timerTaskPing = new TimerTaskPing();
		timerPing.scheduleAtFixedRate(timerTaskPing, TimeConstants.PING_TIME,
				TimeConstants.PING_TIME);
	}
}
