package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;

/**
 * An interface for objects that handles the communications between the server
 * and a client
 * 
 * @author Stefano
 * 
 */
public interface ClientHandler {
	public Move askMove();

	public void executeMove(Move moveToExecute);

	public void sayMoveIsNotValid();
}
