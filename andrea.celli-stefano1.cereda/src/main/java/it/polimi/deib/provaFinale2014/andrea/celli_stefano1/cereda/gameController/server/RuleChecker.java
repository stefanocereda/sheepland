package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.PlayerAction;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.TerrainType;

import java.util.List;

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
	public boolean isValidMove(Move moveToCheck, List<Move> oldMoves,
			BoardStatus actualStatus) {
		// If the move is done by a player check if it's the right player, if
		// it's a valid move and if it's in a valid turn
		if (moveToCheck.getClass() == MoveSheep.class
				|| moveToCheck.getClass() == MovePlayer.class
				|| moveToCheck.getClass() == BuyCardMove.class)
			return isCorrectPlayer((PlayerAction) moveToCheck, actualStatus)
					&& isCorrectMove(moveToCheck, actualStatus)
					&& isAffordable((PlayerAction) moveToCheck, actualStatus)
					&& isCorrectTurn((PlayerAction) moveToCheck, oldMoves);

		// otherwise (a move done automatically, like the balcksheep at the end
		// of the turn, only check if it'a valid move
		else
			return isCorrectMove(moveToCheck, actualStatus);

	}

	/**
	 * Check if the move is done by (and on) the current player
	 * 
	 * @param move
	 *            The PlayerActionMove to check (a move done by a player)
	 * @param boardStatus
	 *            The actual status
	 * @return if the move is done by the current player
	 */
	private boolean isCorrectPlayer(PlayerAction move, BoardStatus boardStatus) {
		if (move.getPlayer() == boardStatus.getCurrentPlayer())
			return true;
		else
			return false;
	}

	/**
	 * Check if a move is affordable
	 * 
	 * @param move
	 *            The PlayerActionMove to check
	 * @param boardStatus
	 *            The current status
	 * @return if the move is affordable for the player
	 */
	private boolean isAffordable(PlayerAction move, BoardStatus boardStatus) {
		int money = move.getPlayer().getMoney();

		// Calculate the cost
		MoveCostCalculator mcc = MoveCostCalculator.create();
		int cost = mcc.getMoveCost(move);

		return (cost <= money);
	}

	/**
	 * Check if a move respect the rules
	 * 
	 * @param move
	 *            The move to be checked
	 * @param boardStatus
	 *            the current status
	 * @return if the move is valid
	 */
	private boolean isCorrectMove(Move move, BoardStatus boardStatus) {
		// call the right sub-method casting the move
		if (move.getClass() == MovePlayer.class)
			return isCorrectMovePlayer((MovePlayer) move, boardStatus);
		if (move.getClass() == MoveSheep.class)
			return isCorrectMoveSheep((MoveSheep) move, boardStatus);
		if (move.getClass() == BuyCardMove.class)
			return isCorrectMoveBuyCard((BuyCardMove) move, boardStatus);
		// it can't be another type (TODO: throw exception?)
		return false;
	}

	/**
	 * Check if a shepherd is moving correctly
	 * 
	 * @param move
	 *            the MovePlayer to check
	 * @param boardStatus
	 *            the current status
	 * @return if the move is valid (=if the player is moving on a free road)
	 * @author Andrea
	 */
	private boolean isCorrectMovePlayer(MovePlayer move, BoardStatus boardStatus) {
		return boardStatus.isFreeRoad(move.getNewPositionOfThePlayer());
	}

	/**
	 * Check if a sheep is moving correctly: The sheep must come from a land
	 * adjacent the shepherd and go to the other one
	 * 
	 * @param move
	 *            the MoveSheep to check
	 * @param boardStatus
	 *            the current status
	 * @return if the move is valid
	 */
	private boolean isCorrectMoveSheep(MoveSheep move, BoardStatus boardStatus) {
		// search the adjacent terrains
		Terrain[] adjacentTerrains = move.getPlayer().getPosition()
				.getAdjacentTerrains();

		// get where the sheep is moving
		Terrain coming = move.getMovedSheep().getPosition();
		Terrain going = move.getNewPositionOfTheSheep();

		// check if the move is actually valid
		if ((coming == adjacentTerrains[0] && going == adjacentTerrains[1])
				|| (coming == adjacentTerrains[1] && going == adjacentTerrains[0]))
			return true;
		else
			return false;
	}

	/**
	 * Check if a buy move is valid: the card must be of the same type of one of
	 * the two adjacent regions
	 * 
	 * @param move
	 *            The BuyCardMove to check
	 * @param boardStatus
	 *            the current status
	 * @return if the move is valid
	 * 
	 */
	private boolean isCorrectMoveBuyCard(BuyCardMove move,
			BoardStatus boardStatus) {
		// get the adjacent terrains
		Terrain[] adjacentTerrains = move.getPlayer().getPosition()
				.getAdjacentTerrains();

		// and their types
		TerrainType t1 = adjacentTerrains[0].getTerrainType();
		TerrainType t2 = adjacentTerrains[1].getTerrainType();

		// and the type of the card
		TerrainType tBuying = move.getNewCard().getTerrainType();

		// now check if the type is valid
		if (t1 == tBuying || t2 == tBuying)
			return true;
		else
			return false;
	}

	/**
	 * Check if this move can be done after the previous ones, a player can't
	 * execute two times in a turn the same kind of move if it hasn't moved the
	 * shepherd in the while
	 * 
	 * @param moveToCheck
	 *            the PlayerAction (a move done by a player) to be checked
	 * @param oldMoves
	 *            the List of the previous moves done in this turn
	 * @return if this move is valid in this turn
	 */
	private boolean isCorrectTurn(PlayerAction moveToCheck, List<Move> oldMoves) {

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
