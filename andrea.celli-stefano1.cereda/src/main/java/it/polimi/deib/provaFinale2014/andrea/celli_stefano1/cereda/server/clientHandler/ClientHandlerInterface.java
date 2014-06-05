package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.clientHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.server.ClientDisconnectedException;

import java.util.List;

/**
 * this is the interface of a client handler, containing the methods called by
 * the game controller to sends messages to the client
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
	 * @throws ClassNotFoundException
	 * @throws ClientDisconnectedException
	 */
	public Move sayMoveIsNotValid() throws ClassNotFoundException,
			ClientDisconnectedException;

	/**
	 * Send to the client a new status to replace the old one
	 * 
	 * @throws ClientDisconnectedException
	 */
	public void sendNewStatus(BoardStatus newStatus)
			throws ClientDisconnectedException;

	/**
	 * This method asks the client to send back a road which will be his
	 * starting position
	 * 
	 * @return The road chosen by the client
	 * @throws ClientDisconnectedException
	 * @throws ClassNotFoundException
	 */
	public Road askInitialPosition() throws ClientDisconnectedException,
			ClassNotFoundException;

	/** This method is used to ask the initial position of the second shepherd */
	public Road askSecondInitialPosition() throws ClientDisconnectedException,
			ClassNotFoundException;

	/**
	 * Ping the client and wait for his answer
	 * 
	 * @throws ClientDisconnectedException
	 */
	void pingTheClient() throws ClientDisconnectedException;

	/**
	 * this method sends to the client a player representing the actual player
	 * 
	 * @param newCurrentPlayer
	 *            The current player
	 * @throws ClientDisconnectedException
	 */
	void setCurrentPlayer(Player newCurrentPlayer)
			throws ClientDisconnectedException;

	/**
	 * This method sends to the client a list of players representing the
	 * winners
	 * 
	 * @param winners
	 *            the list of winners
	 * @throws ClientDisconnectedException
	 */
	void sendWinners(List<Player> winners) throws ClientDisconnectedException;

	/** This method is used to let the client know the controlled player */
	void notifyControlledPlayer(Player controlled)
			throws ClientDisconnectedException;

	/**
	 * This method asks the client to choose the controlled shepherd (true means
	 * the second)
	 */
	boolean chooseShepherd() throws ClientDisconnectedException;
}
