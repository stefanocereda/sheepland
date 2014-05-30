package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.rmi.RemoteException;
import java.util.List;

/**
 * This is the implementation of RMIInterface, for every method calls the
 * equivalent on the client game controller
 * 
 * @author Stefano
 * 
 */
public class RMIImpl implements RMIInterface {
	private GameControllerClient controller;

	public RMIImpl(GameControllerClient controller) {
		this.controller = controller;
	}

	public Move getMove() throws RemoteException {
		return controller.getNewMove();
	}

	public void executeMove(Move moveToExecute) throws RemoteException {
		controller.executeMove(moveToExecute);
	}

	public Move notifyNotValidMove() throws RemoteException {
		controller.notifyNotValidMove();
		return controller.getNewMove();
	}

	public void updateStatus(BoardStatus newStatus) throws RemoteException {
		controller.upDateStatus(newStatus);
	}

	public void setCurrentPlayer(Player newCurrentPlayer)
			throws RemoteException {
		controller.setCurrentPlayer(newCurrentPlayer);
	}

	public void sendWinners(List<Player> winners) throws RemoteException {
		controller.notifyWinners(winners);
		// TODO handle closing connection
	}

	public void ping() throws RemoteException {
		// empty method
	}

	public Road askInitialPosition() throws RemoteException {
		return controller.chooseInitialPosition();
	}

	public void notifyControlledPlayer(Player controlled)
			throws RemoteException {
		controller.setControlledPlayer(controlled);
	}
}
