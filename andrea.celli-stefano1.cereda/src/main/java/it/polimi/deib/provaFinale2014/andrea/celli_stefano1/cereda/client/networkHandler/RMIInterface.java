package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * This is the interface of a client's network handler. It is primarily used to
 * publish on the rmi registry the supported methods
 * 
 * @author Stefano
 */
public interface RMIInterface extends Remote {
	/** This method is invoked by the server when it wants to receive a move */
	public Move getMove() throws RemoteException;

	/** This method is invoked by the server when there's a move to execute */
	public void executeMove(Move moveToExecute) throws RemoteException;

	/**
	 * This method is called by the server to say that the last move wasn't
	 * valid and it's expecting a new one
	 */
	public Move notifyNotValidMove() throws RemoteException;

	/**
	 * This method is called by the server to send a board status to substitute
	 * the old one
	 */
	public void updateStatus(BoardStatus newStatus) throws RemoteException;

	/** This method is used to choose the initial road */
	public Road askInitialPosition() throws RemoteException;

	/** This method is used to check connection */
	public void ping() throws RemoteException;

	/** This method is called by the server to specify who is the current player */
	public void setCurrentPlayer(Player newCurrentPlayer)
			throws RemoteException;

	/**
	 * This method is used to notify the end of the game and receive the list of
	 * winners
	 */
	public void sendWinners(ArrayList<Player> winners) throws RemoteException;

}
