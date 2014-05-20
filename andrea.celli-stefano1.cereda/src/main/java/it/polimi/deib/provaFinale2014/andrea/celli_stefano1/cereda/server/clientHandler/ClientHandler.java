package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import java.io.IOException;

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
	public Move askMove() throws ClassNotFoundException, IOException;

	public void executeMove(Move moveToExecute) throws IOException;

	public void sendNewStatus(BoardStatus newStatus) throws IOException;

	public Move sayMoveIsNotValid() throws ClassNotFoundException, IOException;
}
