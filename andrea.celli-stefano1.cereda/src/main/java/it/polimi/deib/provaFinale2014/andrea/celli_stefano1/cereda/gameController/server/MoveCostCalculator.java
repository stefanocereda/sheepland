/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;

/**
 * Static class to calculate the cost of a move
 * 
 * TODO tests
 * 
 * @author Stefano
 * 
 */
public class MoveCostCalculator {
	/** Hide the default constructor */
	private MoveCostCalculator() {
	}

	/**
	 * Calculate the cost of a move
	 * 
	 * @param move
	 *            the move to evaluate
	 * @return the move cost
	 */
	public static int getMoveCost(Move move, BoardStatus boardStatus) {
		if (move.getClass().isInstance(MovePlayer.class)) {
			return getMoveCostPlayer((MovePlayer) move, boardStatus);
		}
		if (move.getClass().isInstance(BuyCardMove.class)) {
			return getMoveCostBuyCard((BuyCardMove) move);
		}
		return 0;
	}

	/**
	 * The movement of a shepherd is free if it'g going on an adjacent road,
	 * otherwise 1.
	 */
	private static int getMoveCostPlayer(MovePlayer move,
			BoardStatus boardStatus) {
		Road coming = boardStatus.getEquivalentPlayer(move.getPlayer())
				.getPosition();
		Road going = move.getNewPositionOfThePlayer();

		// the first move of the shepherd has coming == null so we return 0, in
		// other moments of the game this kind of move is not valid, but that is
		// handled by the rulechecker
		if (coming == null) {
			return 0;
		}

		// check if going is in coming's adjacent roads
		boolean adjacent = coming.getNextRoads().contains(going);

		// return the cost
		if (adjacent) {
			return 0;
		} else {
			return 1;
		}
	}

	/** Buying a card costs the number on the card */
	private static int getMoveCostBuyCard(BuyCardMove move) {
		return move.getNewCard().getNumber();
	}
}