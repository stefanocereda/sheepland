package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ClientDisconnectedException;

/**
 * this is the interface of a client handler, containing the methods called by
 * the game controller
 */

public interface ClientHandlerInterface {
	/**
	 * This method asks the client to send a new move, which is returned. The
	 * client can give an impossible move so it must be checked
	 * 
	 * @return the move returned from the client
	 * @throws ClientDisconnectedException
	 * @throws ClassNotFoundException
	 */
	public Move askMove() throws ClassNotFoundException,
			ClientDisconnectedException;

	/**
	 * Send the client a move to be executed. The clients doesn't do any check
	 * on the move so it must be already valid
	 * 
	 * @param moveToExecute
	 *            to move to be executed
	 * @throws ClientDisconnectedException
	 */
	public void executeMove(Move moveToExecute)
			throws ClientDisconnectedException;

	/**
	 * Say to the client that the last move wasn't valid, and waits for a new
	 * one
	 * 
	 * @return a new Move
	 * @throws ClientDisconnectedException
	 * @throws ClassNotFoundException
	 */
	public Move sayMoveIsNotValid() throws ClassNotFoundException,
			ClientDisconnectedException;

	/**
	 * Send to the client a new status to replace the old one
	 * 
	 * @throws ClientDisconnectedException
	 * 
	 */
	public void sendNewStatus(BoardStatus newStatus)
			throws ClientDisconnectedException;

	/**
	 * Ping the client and throw a ClientDisconnectedException if it's
	 * disconnected
	 * 
	 * @throws ClientDisconnectedException
	 */
	void pingTheClient() throws ClientDisconnectedException;

}
