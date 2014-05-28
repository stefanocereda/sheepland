package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.Interface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.ExecuteAction;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveBlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.PlayerAction;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manage the game in the client executing the up-dates that are
 * received from the server. There are a finite number of "instruction" from the
 * server that has to be taken into consideration, for each of them this class
 * contains a method that can be invoked in the part of the code dedicated to
 * the communication with the server.
 * 
 * @author Andrea
 * @TODO askNewMove and notValidMove
 */
public class GameControllerClient {
	private ExecuteAction executeAction;
	/** The boardStatus of the current game */
	private BoardStatus boardStatus;
	/** The user interface */
	private Interface userInterface;

	/** The constructor sets the interface */
	public GameControllerClient(Interface userInterface) {
		this.userInterface = userInterface;
		userInterface.setReferenceToGameController(this);
	}

	/**
	 * @return boardStatus the board status of the current game
	 */
	public BoardStatus getBoardStatus() {
		return boardStatus;
	}

	/**
	 * This method links executeAction with a proper instance of the class. This
	 * method will be overridden in the GameControllerClienExtended class (that
	 * class will inherit from GameControllerClient). In this way the
	 * GameControllerClientExtended class is going to have an
	 * ExecuteActionExtended attribute (it will use the same of its superclass
	 * through dynamic binding.) This method links boardStatus with a proper
	 * instance of the class. This method will be overridden in the
	 * GameControllerExtended class. It will use dynamic binding to use the
	 * boardStatus attribute of the superclass as a BoardStatusExtended.
	 * 
	 * @param numberOfPlayers
	 *            The number of players that will play the match
	 */
	public void initializeGame(int numberOfPlayers) {
		// It starts a new update executer
		executeAction = new ExecuteAction();
		// It creates a new boardStatus
		boardStatus = new BoardStatus(numberOfPlayers);
	}

	/**
	 * This method updates the boardStatus "owned" by a client with the new
	 * information provided by the server after the creation of the game. This
	 * method has to be used only once in the course of a game.
	 * 
	 * @param newBoardStatus
	 *            the boardStatus with updates
	 * @TODO introdurre un controllo sulla correttezza di newBoardStatus con
	 *       eventuali eccezioni?
	 */
	public void upDateStatus(BoardStatus newBoardStatus) {
		this.boardStatus = newBoardStatus;
	}

	/**
	 * This method takes as an input parameter a generic move and executes it.
	 * This couls be done using Late Binding but, to do so, this methods should
	 * have been placed in move classes. Therefore the distinction between model
	 * and controller would have been compromised.
	 * 
	 * @param move
	 *            the move that has to be executed
	 */
	public void executeMove(Move move) {
		if (move instanceof PlayerAction) {
			if (move instanceof BuyCardMove) {
				executeAction.executeBuyCardMove((BuyCardMove) move,
						boardStatus);
			} else {
				if (move instanceof MovePlayer)
					executeAction.executeMovePlayer((MovePlayer) move,
							boardStatus);
				else if (move instanceof MoveSheep)
					executeAction.executeMoveSheep((MoveSheep) move,
							boardStatus);
			}
		} else if (move instanceof MoveBlackSheep)
			executeAction.executeMoveBlackSheep((MoveBlackSheep) move,
					boardStatus);

		userInterface.notifyMove(move);
	}

	public void notifyNotValidMove() {
		userInterface.notifyNotValidMove();
	}

	public Move getNewMove() {
		return userInterface.getNewMove();
	}

	public void notifyDisconnection() {
		userInterface.notifyDisconnection();
	}

	public void setCurrentPlayer(Player newCurrentPlayer) {
		boardStatus.setCurrentPlayer(newCurrentPlayer);
		userInterface.notifyCurrentPlayer(newCurrentPlayer);
	}

	public void notifyWinners(List<Player> winners) {
		userInterface.notifyWinners(winners);
	}

	public Road chooseInitialPosition() {
		return userInterface.chooseInitialPosition();
	}

	public void setControlledPlayer(Player controlled) {
		// TODO
	}

}
