package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.PlayerAction;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Deck;
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

		// otherwise (a move done automatically, like the blacksheep at the end
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
		if (move.getPlayer().equals(boardStatus.getCurrentPlayer()))
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
	 * Check if a shepherd is moving correctly (moving on a free road)
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
		if ((coming.equals(adjacentTerrains[0]) && going
				.equals(adjacentTerrains[1]))
				|| (coming.equals(adjacentTerrains[1]) && going
						.equals(adjacentTerrains[0])))
			return true;
		else
			return false;
	}

	/**
	 * Check if a buy move is valid: the card must be of the same type of one of
	 * the two adjacent regions. It's also not valid if it's a card not in the
	 * deck or if there's cheaper card of the same type
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
		// get the type of the card
		TerrainType tBuying = move.getNewCard().getTerrainType();

		// check if it's in the deck
		Deck deck = boardStatus.getDeck();
		if (!deck.contains(move.getNewCard()))
			return false;

		// check for cheaper cards
		for (Card c : deck) {
			if (c.getTerrainType().equals(tBuying)
					&& c.getNumber() < move.getNewCard().getNumber())
				return false;
		}

		// get the adjacent terrains
		Terrain[] adjacentTerrains = move.getPlayer().getPosition()
				.getAdjacentTerrains();

		// and their types
		TerrainType t1 = adjacentTerrains[0].getTerrainType();
		TerrainType t2 = adjacentTerrains[1].getTerrainType();

		// now check if the type is valid
		if (t1.equals(tBuying) || t2.equals(tBuying))
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

		// the second is correct if it's different from the first or if they're
		// both move player
		if (oldMoves.size() == 1)
			return ((!firstMoveType.equals(thisMoveType))
					|| (firstMoveType.equals(MovePlayer.class)) || (thisMoveType
						.equals(MovePlayer.class)));

		// this is the third move, is correct if: 1)they're all different 2)it's
		// equals to the first and the second is a moveplayer 3)it's equals to
		// the second and they're both moveplayer => all different or the second
		// is a move player
		Object secondMoveType = oldMoves.get(1).getClass();
		return (((!firstMoveType.equals(thisMoveType)) && (!secondMoveType
				.equals(thisMoveType))) || (secondMoveType
				.equals(MovePlayer.class)));
	}
}
