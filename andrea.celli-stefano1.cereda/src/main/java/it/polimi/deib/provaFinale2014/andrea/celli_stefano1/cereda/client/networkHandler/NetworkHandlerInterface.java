package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This is the interface of a client's network handler. It is primarily used to
 * publish on the rmi registry the supported methods
 */
public interface NetworkHandlerInterface extends Remote {
	public Move getMove() throws RemoteException;

	public void executeMove(Move moveToExecute) throws RemoteException;

	public Move notifyNotValidMove() throws RemoteException;

	public void updateStatus(BoardStatus newStatus) throws RemoteException;

	public void ping() throws RemoteException;

	public void setCurrentPlayer(Player newCurrentPlayer)
			throws RemoteException;
}
