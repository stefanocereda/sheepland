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

	public boolean add(ClientHandler acceptedHandler);

	public ClientHandler[] toArray(ClientHandler[] array);

	public ClientHandler set(int i, ClientHandler newClienthandler);

	public ClientHandler get(int i);
}
