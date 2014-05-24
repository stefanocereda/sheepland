package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.networkHandler;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;

/**
 * This abstract class is used for the methods in common between RMI and socket:
 * the methods that operate on the model and on the interface
 * 
 * @author Stefano
 * 
 */
public abstract class NetworkHandler implements NetworkHandlerInterface {
	/** A reference to the client's controller */
	protected GameControllerClient controller;

	public NetworkHandler(GameControllerClient gameController) {
		controller = gameController;
	}

	public Move getMove() {
		return controller.getNewMove();
	}

	public void executeMove(Move moveToExecute) {
		controller.executeMove(moveToExecute);
	}

	public Move notifyNotValidMove() {
		controller.notifyNotValidMove();
		return controller.getNewMove();
	}

	public void updateStatus(BoardStatus newStatus) {
		controller.upDateStatus(newStatus);
	}
}
