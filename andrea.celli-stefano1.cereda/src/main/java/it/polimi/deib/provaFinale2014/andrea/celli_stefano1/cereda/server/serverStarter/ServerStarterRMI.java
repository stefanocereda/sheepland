package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.GameType;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.listOfClientHandler.ListOfClientHandlerRMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * The RMI version of a server starter. This class creates and exports an RMI
 * connector, which is called by the clients, this connector gives an unique id
 * to each client and notifies it to this server starter. Then the server
 * starter search for an object with that id and uses it as a client handler
 * 
 * @author Stefano
 */
public class ServerStarterRMI extends ServerStarter {
	/** The rmi registry */
	private Registry registry;
	/** The port of the registry */
	private int registryPort;

	/**
	 * Creates an rmi server starter
	 * 
	 * @param maxPlayers
	 *            number of max players in a game
	 * @param minutesWaiting
	 *            minutes waiting for maxPlayers
	 * @param gameType
	 *            set of rules to use
	 * @param port
	 *            ip port of the registry
	 */
	public ServerStarterRMI(int maxPlayers, GameType gameType, long delay,
			int port) {
		super(maxPlayers, gameType, delay);
		this.registryPort = port;
		clientHandlers = new ListOfClientHandlerRMI();
	}

	@Override
	public void start() {
		// create an RMI registry and publish an object used to connect
		RMIConnector connector = new RMIConnectorImpl(this);
		RMIConnector connectorStub = UnicastRemoteObject.exportObject(
				connector, 0);
		registry = LocateRegistry.createRegistry(registryPort);
		registry.rebind(RMICostants.CONNECTOR, connectorStub);
	}

	/**
	 * This method is called by the rmi connector when a client connects, it
	 * searches for the client handler in the registry and adds it to a list of
	 * waiting clients, if possible it starts a game
	 * 
	 * @param remoteName
	 *            the name to look for in the registry
	 */
	public void notifyConnection(String remoteName) {
		ClientHandler acceptedHandler = (ClientHandler) registry
				.lookup(remoteName);

		// if it's a previously disconnected client we don't need to handle
		// it here
		if (handlePreviouslyDisconnected(acceptedHandler)) {
			break;
		}

		// otherwise add the clienthandler to the waiting list
		clientHandlers.add(acceptedHandler);

		// try to start the timer
		startTimer();

		// if we have the right number of players awaiting we start the game
		startGame();

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
