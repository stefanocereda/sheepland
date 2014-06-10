/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.gameController.GameControllerClient;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketOffer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.util.List;

/**
 * That's the interface of a user interface. It specifies the public methods
 * that will be called by the client game controller
 * 
 * @author Stefano
 * 
 */
public interface Interface {

	/**
	 * This methods links the InterfaceConsole with the GameController. This
	 * passage couldn't be done in the initialization because they are created
	 * at the same time.
	 * 
	 * @param the
	 *            game controller that has to be linked
	 */
	void setReferenceToGameController(GameControllerClient gameControllerClient);

	/**
	 * Tell the user that the game is starting. This method should be called
	 * only when we receive the initial boardStatus, when we should have all the
	 * parameters set EXCEPT THE PLAYERS POSITIONS THAT ARE STILL NULL so don't
	 * try to use them!
	 */
	void showInitialInformation();

	/** Tell the user that the server sent a brand new board status and show it */
	void notifyNewStatus();

	/**
	 * Ask the user to choose a road that will be his initial position, the
	 * server will keep asking until we choose a free road. THE POSITION OF THE
	 * PLAYERS AT THIS POINT COULD BE STILL NULL, SO CHECK IT!!
	 * 
	 * @return the road chosen by the player
	 */
	Road chooseInitialPosition();

	/**
	 * Show a move to the user, the move will be executed on the board status
	 * after this method, so the interface has the old positions on the
	 * boardStatus and the new ones in the Move
	 * 
	 * @Move the move to show
	 */
	void notifyMove(Move move);

	/**
	 * Ask the user to insert a new move
	 * 
	 * @return the move inserted by the user
	 */
	Move getNewMove();

	/** Tell the user that his last move has been rejected by the server */
	void notifyNotValidMove();

	/**
	 * Tell the user that the current player is changed
	 * 
	 * @param newCurrentPlayer
	 *            the new current player
	 */
	void notifyCurrentPlayer(Player newCurrentPlayer);

	/**
	 * Tell the user that the game has come to an end
	 * 
	 * @param winners
	 *            the list of players that have realized the max points
	 */
	void notifyWinners(List<Player> winners);

	/** Tell the user that we are disconnected and trying to reconnect */
	void notifyDisconnection();

	/** Ask the user to choose a shepherd for the current turn */
	boolean chooseShepherd();

	/** Ask the user an initial road for his second shepherd */
	Road chooseSecondInitialPosition();

	/** Tell the user if the current player is using his second shepherd */
	void notifyShepherd(boolean usingSecond);

	/**
	 * Ask the user to choose some cards that he wants to sell and the price
	 * required
	 */
	List<MarketOffer> askMarketOffers();
}
