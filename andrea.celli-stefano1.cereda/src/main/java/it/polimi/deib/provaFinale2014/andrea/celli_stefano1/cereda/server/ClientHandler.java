package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;

/**
 * An interface for objects that handles the communications between the server
 * and a client
 * 
 * @author Stefano
 * 
 */
public interface ClientHandler {
	public Move getNextMove();

	public void sendErrorMessage();

	public void sendNewStatus(BoardStatus newStatus);
}
