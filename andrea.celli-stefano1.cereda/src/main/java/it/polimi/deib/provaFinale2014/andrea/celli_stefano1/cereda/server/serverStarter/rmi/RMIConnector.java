package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.rmi;

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
	 * (as a string) for bind his network manager in the registry. It also
	 * notifies the server starter that there is a client with that id to be
	 * handled
	 * 
	 * @param id
	 *            The client id
	 * @return The new client id
	 * @throws RemoteException
	 */
	public int connect(Integer id) throws RemoteException;

}