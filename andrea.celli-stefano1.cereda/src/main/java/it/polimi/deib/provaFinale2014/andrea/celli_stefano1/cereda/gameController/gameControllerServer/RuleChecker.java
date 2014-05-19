package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerServer;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.TerrainType;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep;

import java.util.ArrayList;

/**
 * A single object (to be shared between games) used to check basic rules
 * 
 * TODO tests, blacksheep move (need something to represent the dice roll)
 * 
 * @author Stefano
 * 
 */
public class RuleChecker {
	private static RuleChecker ruleChecker;

	/** private constructor for singleton pattern */
	private RuleChecker() {
	}

	/**
	 * A singleton pattern constructor
	 * 
	 * @return a rulechecker for the basic rules
	 */
	public static RuleChecker create() {
		if (ruleChecker == null)
			ruleChecker = new RuleChecker();
		return ruleChecker;
	}

	/**
	 * Check if a move is valid
	 * 
	 * @param moveToCheck
	 *            The move to be checked
	 * @param oldMoves
	 *            An ArrayList of the previous moves of the player in this turn
	 * @param actualStatus
	 *            The actual status of the game
	 * @return true if the move is valid
	 */
	public boolean isValidMove(Move moveToCheck, ArrayList<Move> oldMoves,
			BoardStatus actualStatus) {
		return isCorrectPlayer(moveToCheck, actualStatus)
				&& isCorrectMove(moveToCheck, actualStatus)
				&& isCorrectTurn(moveToCheck, oldMoves);

	}

	/** Check if the move is done by (and on) the current player */
	private boolean isCorrectPlayer(Move move, BoardStatus boardStatus) {
		if (move.getPlayer() == boardStatus.getCurrentPlayer())
			return true;
		else
			return false;
	}

	/**
	 * We use the dynamic binding to do the correct check based on the type of
	 * move, so this method shouldn't be really used
	 */
	private boolean isCorrectMove(Move move, BoardStatus boardStatus) {
		return false;
	}

	/**
	 * Check if the shepherd has enough money to move in a road which isn't
	 * adjacent
	 * 
	 * @author Andrea
	 */
	private static boolean hasEnoughMoney(MovePlayer move) {
		return move.getPlayer().getMoney() >= move.getCost();
	}

	/**
	 * Check if the shepherd is moving correctly(going on a free road)
	 * 
	 * @author Andrea
	 */
	private static boolean isMovingInAFreeRoad(MovePlayer move,
			BoardStatus boardStatus) {
		return boardStatus.isFreeRoad(move.getNewPositionOfThePlayer());
	}

	/**
	 * Puts togheter the two previous conditions
	 * 
	 * @param move
	 * @param boardStatus
	 * @return true if the MovePlayer move can be executed
	 * @author Andrea
	 */
	private boolean isCorrectMove(MovePlayer move, BoardStatus boardStatus) {
		return isMovingInAFreeRoad(move, boardStatus) && hasEnoughMoney(move);
	}

	/**
	 * Check if a sheep is moving correctly: The sheep must come from a land
	 * adjacent the shepherd and go to the other one
	 */
	private boolean isCorrectMove(MoveSheep move, BoardStatus boardStatus) {
		Terrain[] adjacentTerrains = move.getPlayer().getPosition()
				.getAdjacentTerrains();
		Terrain coming = move.getMovedSheep().getPosition();
		Terrain going = move.getNewPositionOfTheSheep();

		if ((coming == adjacentTerrains[0] && going == adjacentTerrains[1])
				|| (coming == adjacentTerrains[1] && going == adjacentTerrains[0]))
			return true;
		else
			return false;
	}

	/**
	 * Check if the card has an affordable price for the shepherd
	 * 
	 * @author Andrea
	 */
	private static boolean isAffordable(BuyCardMove move) {
		return move.getCardPrice() <= move.getPlayer().getMoney();
	}

	/**
	 * Check if a buy move is valid: the card must be of the same type of one of
	 * the two adjacent regions
	 */
	private boolean isCorrectMove(BuyCardMove move, BoardStatus boardStatus) {
		Terrain[] adjacentTerrains = move.getPlayer().getPosition()
				.getAdjacentTerrains();

		TerrainType t1 = adjacentTerrains[0].getTerrainType();
		TerrainType t2 = adjacentTerrains[1].getTerrainType();

		TerrainType tBuying = move.getNewCard().getTerrainType();

		if ((t1 == tBuying || t2 == tBuying) && isAffordable(move))
			return true;
		else
			return false;
	}

	/** Check if this move can be done after the previous ones */
	private boolean isCorrectTurn(Move moveToCheck, ArrayList<Move> oldMoves) {

		// the first move is always correct
		if (oldMoves.size() == 0)
			return true;

		// we have at least one oldMove
		Object firstMoveType = oldMoves.get(0).getClass();
		Object thisMoveType = moveToCheck.getClass();

		// the second is correct if it's different from the first or if one of
		// the two is a shepherd move
		if (oldMoves.size() == 1)
			return ((firstMoveType != thisMoveType)
					|| (firstMoveType == MovePlayer.class) || (thisMoveType == MovePlayer.class));

		// this is the third move, is correct if it's different from both the
		// previous or if one of the three is a shepherd move
		Object secondMoveType = oldMoves.get(1).getClass();
		return (((firstMoveType != thisMoveType) && (secondMoveType != thisMoveType))
				|| (firstMoveType == MovePlayer.class)
				|| (secondMoveType == MovePlayer.class) || (thisMoveType == MovePlayer.class));

	}

}
