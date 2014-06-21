package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.Interface;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketBuy;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketOffer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.PlayerDouble;

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
 */
public class GameControllerClient {
	/** The boardStatus of the current game */
	private BoardStatus boardStatus;
	/** The user interface */
	private Interface userInterface;
	/** The player controlled */
	private Player controlledPlayer;

	/**
	 * The constructor sets the interface
	 * 
	 * @param userInterface
	 *            the user interface
	 */
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
	 * This method updates the boardStatus "owned" by a client with the new
	 * information provided by the server after the creation of the game. When
	 * we receive the status for the first time we notify to the client that the
	 * game is starting.
	 * 
	 * @param newBoardStatus
	 *            the boardStatus with updates
	 */
	public void upDateStatus(BoardStatus newBoardStatus) {
		BoardStatus old = this.boardStatus;

		if (old == null) {
			this.boardStatus = newBoardStatus;
			userInterface.showInitialInformation();
		} else {
			this.boardStatus = newBoardStatus;
			userInterface.notifyNewStatus();
		}
	}

	/**
	 * This method is called by the server at the beginning of the game. We ask
	 * the user for a road and give it back, the server will keep asking until
	 * we choose a free road
	 * 
	 * @return the road chosen by the interface
	 */
	public Road chooseInitialPosition() {
		return userInterface.chooseInitialPosition();
	}

	/**
	 * This method takes as an input parameter a generic move and notifies the
	 * user, then executes it.
	 * 
	 * @param move
	 *            the move that has to be executed
	 */
	public void executeMove(Move move) {
		userInterface.notifyMove(move);
		move.execute(boardStatus);
	}

	/**
	 * This method is called by the server to retrieve a new move, we ask it to
	 * the user and return
	 * 
	 * @return The move chosen in the interface
	 */
	public Move getNewMove() {
		return userInterface.getNewMove();
	}

	/**
	 * This method is called by the server when we sent an invalid move, we
	 * notify it to the user
	 */
	public void notifyNotValidMove() {
		userInterface.notifyNotValidMove();
	}

	/**
	 * This method is called after a player change, we notify the user and set
	 * the current player
	 * 
	 * @param newCurrentPlayer
	 *            The player that has to become the new current player
	 */
	public void setCurrentPlayer(Player newCurrentPlayer) {
		userInterface.notifyCurrentPlayer(newCurrentPlayer);
		boardStatus.setCurrentPlayer(boardStatus
				.getEquivalentPlayer(newCurrentPlayer));
	}

	/**
	 * This method is called by the server at the end of a game to notify the
	 * list of winners
	 * 
	 * @param winners
	 *            A list of all the winners
	 */
	public void notifyWinners(List<Player> winners) {
		userInterface.notifyWinners(winners);
	}

	/**
	 * This method is called by the network handler when it detects a
	 * disconnection, we notify the user of the disconnection
	 */
	public void notifyDisconnection() {
		userInterface.notifyDisconnection();
	}

	/**
	 * This method is the first that is called by the server, it sets the
	 * reference to the controlled player. We don't notify nothing to the client
	 * as we still don't have a boardStatus
	 * 
	 * @param controlled
	 *            The player controlled by this client.
	 */
	public void setControlledPlayer(Player controlled) {
		this.controlledPlayer = controlled;
	}

	/**
	 * @return the player controlled by this client, we ask the boardStatus for
	 *         the equivalent player (id-based) therefore it is safe to use this
	 *         method in the interface
	 */
	public Player getControlledPlayer() {
		return boardStatus.getEquivalentPlayer(controlledPlayer);
	}

	/**
	 * This method asks the client to choose a controlled shepherd and sets it
	 * in the model
	 * 
	 * @return If the player wants to use his second shepherd
	 */
	public boolean getShepherd() {
		boolean second = userInterface.chooseShepherd();
		((PlayerDouble) boardStatus.getCurrentPlayer()).setShepherd(second);
		return second;
	}

	/**
	 * This method ask the client to choose a position for his second shepherd.
	 * We also set our player as controlling the second shepherd, in this way we
	 * will recognize the initial move as done by the right shepherd.
	 * 
	 * @return The road chosen as first position for the second shepherd
	 */
	public Road chooseSecondInitialPosition() {
		((PlayerDouble) boardStatus.getCurrentPlayer()).setShepherd(true);

		return userInterface.chooseSecondInitialPosition();
	}

	/**
	 * This method is used to let the client know which shepherd is using the
	 * current player. We notify the user and sets it in the controller.
	 * 
	 * @param usingSecond
	 *            Set to true if the current player is using his second shepherd
	 */
	public void notifyShepherd(boolean usingSecond) {
		userInterface.notifyShepherd(usingSecond);
		((PlayerDouble) boardStatus.getCurrentPlayer())
				.setShepherd(usingSecond);
	}

	/**
	 * This method asks the user to choose some cards to sell
	 * 
	 * @return A list of market offers
	 */
	public List<MarketOffer> askMarketOffers() {
		List<MarketOffer> toReturn = userInterface.askMarketOffers();

		if (toReturn == null) {
			toReturn = new ArrayList<MarketOffer>();
		}

		return toReturn;
	}

	/**
	 * This method asks the user too choose wich offers he wants to buy
	 * 
	 * @param offers
	 *            A list of marketOffers to choose from (can contain offers done
	 *            by this player)
	 * @return the list of MarketBuy chosen by the user among the offers
	 */
	public List<MarketBuy> askMarketBuy(List<MarketOffer> offers) {
		List<MarketBuy> toReturn = userInterface.askMarketBuy(offers);

		if (toReturn == null) {
			toReturn = new ArrayList<MarketBuy>();
		}

		return toReturn;
	}
}
