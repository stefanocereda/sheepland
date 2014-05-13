package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server;

/**
 * An interface for objects that handles the communications between the server
 * and a client
 * 
 * @author Stefano
 * TODO
 */
public interface ClientHandler {
	public Move getNextMove();

	public void sendErrorMessage();

	public void sendNewStatus(BoardStatus newStatus);
}
