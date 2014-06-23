package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.rmi;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler.RMIInterface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.ServerStarterRMI;

import java.rmi.RemoteException;

/** The implementation of RMIConnector */
public class RMIConnectorImpl implements RMIConnector {
	/** A reference to the RMI server starter */
	private ServerStarterRMI serverStarter;
	/** an identificator of this client */
	private int token;

	/**
	 * This constructor takes as input a reference to the rmi server starter
	 * that will be used to notify the server of new connections
	 */
	public RMIConnectorImpl(ServerStarterRMI serverStarterRMI) {
		serverStarter = serverStarterRMI;
	}

	public int connect(Integer id) throws RemoteException {
		// if the client sends zero is the first connection
		if (id == 0) {
			id = serverStarter.getNewToken();
			// otherwise it'reconnecting (id = id)
		}

		token = id;
		// return the right id to the client
		return id;
	}

	public void notify(RMIInterface client) throws RemoteException {
		serverStarter.notifyConnection(client, token);
	}

	public void ping() throws RemoteException {
		// empty method
	}
}
