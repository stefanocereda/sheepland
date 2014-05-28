package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.rmi;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.serverStarter.ServerStarterRMI;

import java.rmi.RemoteException;

/** The implementation of RMIConnector */
public class RMIConnectorImpl implements RMIConnector {
	/** A counter of created objects */
	private static int created = 0;
	/** A reference to the RMI server starter */
	private ServerStarterRMI serverStarter;

	/**
	 * This constructor takes as input a reference to the rmi server starter
	 * that will be used to notify the server of new connections
	 */
	public RMIConnectorImpl(ServerStarterRMI serverStarterRMI) {
		serverStarter = serverStarterRMI;
	}

	public int connect(Integer id) throws RemoteException {
		// if the client sends zero is the first connection
		if (id == 0)
			id = ++created;
		// otherwise it'reconnecting (id = id)

		// notify the server starter on a separate thread
		Notifier notifier = new Notifier(id.toString());
		Thread notifierThread = new Thread(notifier);
		notifierThread.start();

		// return the right id to the client
		return id;
	}

	/**
	 * This class is used to notify the rmi server starter while returning the
	 * result to the client
	 */
	class Notifier implements Runnable {
		private String serverName;

		public Notifier(String name) {
			serverName = name;
		}

		public void run() {
			serverStarter.notifyConnection(serverName);
		}
	}

	public void ping() throws RemoteException {
		// empty method
	}
}
