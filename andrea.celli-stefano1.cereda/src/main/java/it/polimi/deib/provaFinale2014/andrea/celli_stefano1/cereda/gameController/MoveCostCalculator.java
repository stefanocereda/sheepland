/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveBlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep;

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
	public MoveCostCalculator create() {
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
		// We use dynamic binding for the various Moves
		return 0;
	}

	/**
	 * The movement of a shepherd is free if it'g going on an adjacent road,
	 * otherwise 1
	 */
	public int getMoveCost(MovePlayer move) {
		Road coming = move.getPlayer().getPosition();
		Road going = move.getNewPositionOfThePlayer();

		// check if going is in coming's adjacent roads
		boolean adjacent = false;
		for (Road r : coming.getNextRoads()) {
			if (r == going) {
				adjacent = true;
				break;
			}
		}

		// return the cost
		if (adjacent) {
			return 0;
		} else {
			return 1;
		}
	}

	/** The movement of a sheep is free */
	public int getMoveCost(MoveSheep move) {
		return 0;
	}

	/** Buying a card costs the number on the card */
	public int getMoveCost(BuyCardMove move) {
		return move.getNewCard().getNumber();
	}

	/** Moving the blacksheep is free */
	public int getMoveCost(MoveBlackSheep move) {
		return 0;
	}
}