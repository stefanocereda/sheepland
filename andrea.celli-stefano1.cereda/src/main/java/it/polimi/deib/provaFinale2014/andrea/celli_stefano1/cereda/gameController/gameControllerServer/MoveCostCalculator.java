/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerServer;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;

/**
 * object with static method to calculate the cost of a basic move
 * 
 * TODO tests
 * 
 * @author Stefano
 * 
 */
public class MoveCostCalculator {
	private static MoveCostCalculator moveCostClaculator;

	private MoveCostCalculator() {
	};

	/** Singleton constructor */
	public static MoveCostCalculator create() {
		if (moveCostClaculator == null) {
			moveCostClaculator = new MoveCostCalculator();
		}
		return moveCostClaculator;
	}

	/**
	 * Calculate the cost of a move
	 * 
	 * @param move
	 *            the move to evaluate
	 * @return the move cost
	 */
	public int getMoveCost(Move move) {
		if (move.getClass() == MovePlayer.class)
			return getMoveCostPlayer((MovePlayer) move);
		if (move.getClass() == BuyCardMove.class)
			return getMoveCostBuyCard((BuyCardMove) move);
		return 0;

	}

	/**
	 * The movement of a shepherd is free if it'g going on an adjacent road,
	 * otherwise 1
	 */
	private int getMoveCostPlayer(MovePlayer move) {
		Road coming = move.getPlayer().getPosition();
		Road going = move.getNewPositionOfThePlayer();

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
	private int getMoveCostBuyCard(BuyCardMove move) {
		return move.getNewCard().getNumber();
	}
}