package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.listOfClientHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler.ClientHandler;

/**
 * This interface contains the methods of ArrayList that we use
 * 
 * @author Stefano
 * 
 */
public interface ListOfClientHandler {

	public int size();

	public void add(ClientHandler acceptedHandler);

	public ClientHandler[] values();

	public void set(int i, ClientHandler newClienthandler);
}
