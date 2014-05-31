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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Static object to check the rules validity
 * 
 * @author Stefano
 * 
 */
public class RuleChecker {
	/** A logger */
	private static Logger logger = Logger
			.getLogger("gameController.server.RuleChecker");
	/** The message logged when we detect a move containg null attributes */
	private static String messageNull = "Detected a move with null attributes, setting it to invalid";

	/** private constructor for singleton pattern */
	private RuleChecker() {
	}

	/** Check if a BuyCardMove is valid */
	public static boolean isValidBuyCard(BuyCardMove move, BoardStatus status) {
		try {
			return isValidPlayerAction(move, status)
					&& isCorrectMoveBuyCard(move, status);
		} catch (NullPointerException e) {
			logger.log(Level.FINE, messageNull, e);
			return false;
		}
	}

	/** Check if a MoveSheep is valid */
	public static boolean isValidMoveSheep(MoveSheep move, BoardStatus status) {
		try {
			return isValidPlayerAction(move, status)
					&& isCorrectMoveSheep(move, status);
		} catch (NullPointerException e) {
			logger.log(Level.FINE, messageNull, e);
			return false;
		}
	}

	/** Check if a MovePlayer is valid */
	public static boolean isValidMovePlayer(MovePlayer move, BoardStatus status) {
		try {
			return isValidPlayerAction(move, status)
					&& isCorrectMovePlayer(move, status);
		} catch (NullPointerException e) {
			logger.log(Level.FINE, messageNull, e);
			return false;
		}
	}

	/** Check if a move that can be done by a player is valid */
	private static boolean isValidPlayerAction(PlayerAction move,
			BoardStatus status) {
		return (isCorrectPlayer(move, status) && isAffordable(move, status) && isCorrectTurn(
				move, status.getCurrentPlayer().getLastMoves()));
	}

	/** Check if an automatic move (not done by a player) is valid */
	public static boolean isValidAutoMove(Move move, BoardStatus status) {
		// TODO COSA SI FA QUI????
		// right now to only time of move we have is the black sheep, it is
		// created by the server so it is always correct
		return true;
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
	private static boolean isCorrectPlayer(PlayerAction move,
			BoardStatus boardStatus) {
		if (move.getPlayer().equals(boardStatus.getCurrentPlayer())) {
			return true;
		} else {
			return false;
		}
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
	private static boolean isAffordable(PlayerAction move,
			BoardStatus boardStatus) {
		int money = move.getPlayer().getMoney();

		// Calculate the cost
		int cost = MoveCostCalculator.getMoveCost(move);

		return (cost <= money);
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
	private static boolean isCorrectMovePlayer(MovePlayer move,
			BoardStatus boardStatus) {
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
	private static boolean isCorrectMoveSheep(MoveSheep move,
			BoardStatus boardStatus) {
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
						.equals(adjacentTerrains[0]))) {
			return true;
		} else {
			return false;
		}
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
	private static boolean isCorrectMoveBuyCard(BuyCardMove move,
			BoardStatus boardStatus) {
		// get the type of the card
		TerrainType tBuying = move.getNewCard().getTerrainType();

		// check if it's in the deck
		Deck deck = boardStatus.getDeck();
		if (!deck.contains(move.getNewCard())) {
			return false;
		}

		// check for cheaper cards
		for (Card c : deck) {
			if (c.getTerrainType().equals(tBuying)
					&& c.getNumber() < move.getNewCard().getNumber()) {
				return false;
			}
		}

		// get the adjacent terrains
		Terrain[] adjacentTerrains = move.getPlayer().getPosition()
				.getAdjacentTerrains();

		// and their types
		TerrainType t1 = adjacentTerrains[0].getTerrainType();
		TerrainType t2 = adjacentTerrains[1].getTerrainType();

		// now check if the type is valid
		if (t1.equals(tBuying) || t2.equals(tBuying)) {
			return true;
		} else {
			return false;
		}
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
	private static boolean isCorrectTurn(PlayerAction moveToCheck,
			List<Move> oldMoves) {

		// the first move is always correct
		if (oldMoves.size() == 0) {
			return true;
		}

		// we have at least one oldMove
		Object firstMoveType = oldMoves.get(0).getClass();
		Object thisMoveType = moveToCheck.getClass();

		// the second is correct if it's different from the first or if they're
		// both move player
		if (oldMoves.size() == 1) {
			return ((!firstMoveType.equals(thisMoveType))
					|| (firstMoveType.equals(MovePlayer.class)) || (thisMoveType
						.equals(MovePlayer.class)));
		}

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
