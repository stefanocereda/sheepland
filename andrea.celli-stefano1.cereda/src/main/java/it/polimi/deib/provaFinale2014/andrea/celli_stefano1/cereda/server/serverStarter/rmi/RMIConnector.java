package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.rmi;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler.RMIInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * An RMI connector is an object deployed on the server and invoked by the
 * client to "log" into the server
 */
public interface RMIConnector extends Remote {
	/**
	 * This method is remotely invoked by the client to registry himself to the
	 * server. The clients pass his id as parameter (0 if it's the first
	 * connection) the server sends back the new id and the client must use it
	 * to create his network manager and then will notify the server.
	 * 
	 * @param id
	 *            The client id
	 * @return The new client id
	 * @throws RemoteException
	 */
	public int connect(Integer id) throws RemoteException;

	/**
	 * This method is called by the client when the network handler is ready,
	 * the server takes it and notify the main server
	 */
	public void notify(RMIInterface client) throws RemoteException;

	/**
	 * A method periodically invoked by the client to check connection
	 */
	public void ping() throws RemoteException;
}
