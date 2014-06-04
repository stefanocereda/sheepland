package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler.RMIInterface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.RMICostants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandlerRMI;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.rmi.RMIConnector;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.rmi.RMIConnectorImpl;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The RMI version of a server starter. This class creates and exports an RMI
 * connector, which is called by the clients. This connector gives an unique id
 * to each client and notifies it to this server starter. Then the server
 * starter creates a client handler that searches on the registry a remote
 * network handler with the name equal to the given id. Now that we have a
 * working client handler we give it back to the server
 * 
 * @author Stefano
 */
public class ServerStarterRMI implements Runnable {
	/** A reference to the server starter that created this object */
	private ServerStarter creator;
	/** The ip port for the registry */
	private int registryPort;

	/** The rmi registry */
	private Registry registry;

	/** A logger */
	private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * This constructor saves the passed parameters, without actually starting
	 * the server
	 * 
	 * @param registryPort
	 *            The IP port for the rmi registry
	 * @param serverCreator
	 *            The object creating this server
	 */
	public ServerStarterRMI(int registryPort, ServerStarter serverCreator) {
		this.registryPort = registryPort;
		this.creator = serverCreator;
	}

	/**
	 * This method publish a Connector object that starts listening for incoming
	 * connections
	 */
	public void run() {
		try {
			// create an RMI registry
			registry = LocateRegistry.createRegistry(registryPort);

			// create a Connector
			RMIConnector connector = new RMIConnectorImpl(this);
			RMIConnector connectorStub = (RMIConnector) UnicastRemoteObject
					.exportObject(connector, 0);

			// publish on the registry
			registry.rebind(RMICostants.CONNECTOR, connectorStub);
		} catch (AccessException e) {
			String message = "Problems starting the rmi server, AccessException";
			logger.log(Level.SEVERE, message, e);
		} catch (RemoteException e) {
			String message = "Problems starting the rmi server, RemoteException";
			logger.log(Level.SEVERE, message, e);
		}
	}

	/**
	 * This method is called by the rmi connector when a client connects, it
	 * takes the client network handler and tries to create a server client
	 * handler. Then notifies the main server
	 * 
	 * 
	 * @param client
	 *            the rmi network handler created by the client
	 */
	public void notifyConnection(RMIInterface client) {

		ClientHandler acceptedHandler;
		try {
			acceptedHandler = new ClientHandlerRMI(creator, client);
			creator.addClient(acceptedHandler);
			// if there is a problem we simply don't register this client
		} catch (RemoteException e) {
			String message = "Problems accepting a client, RemoteException";
			logger.log(Level.SEVERE, message, e);
		}
	}

	/** This method stops the rmi server by unbinding the connector */
	public void stop() {

		try {
			registry.unbind(RMICostants.CONNECTOR);
		} catch (AccessException e) {
			String message = "Problems closing the rmi server";
			logger.log(Level.SEVERE, message, e);
		} catch (RemoteException e) {
			String message = "Problems closing the rmi server";
			logger.log(Level.SEVERE, message, e);
		} catch (NotBoundException e) {
			String message = "Problems closing the rmi server";
			logger.log(Level.SEVERE, message, e);
		}
	}
}
