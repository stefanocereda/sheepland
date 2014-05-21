package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.DisconnectedClient;

import java.io.IOException;

/**
 * Interface for a server
 * 
 * @author Stefano
 * 
 */
public interface ServerStarter {
	/**
	 * Start the server
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException;

	/** Notify to the server starter that a client disconnected */
	public void notifyDisconnection(DisconnectedClient disconnected);

	/** Close the server */
	public void closeServer() throws IOException;
}
