package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatusExtended;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.BlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.TypeOfSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Deck;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketBuy;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.MarketOffer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.TerrainType;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

import java.util.ArrayList;
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
	private static final Logger LOGGER = Logger
			.getLogger("gameController.server.RuleChecker");
	/** The message logged when we detect a move containing null attributes */
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
			LOGGER.log(Level.FINE, messageNull, e);
			return false;
		}
	}

	/** Check if a MoveSheep is valid */
	public static boolean isValidMoveSheep(MoveSheep move, BoardStatus status) {
		try {
			return isValidPlayerAction(move, status)
					&& isCorrectMoveSheep(move, status);
		} catch (NullPointerException e) {
			LOGGER.log(Level.FINE, messageNull, e);
			return false;
		}
	}

	/** Check if a MovePlayer is valid */
	public static boolean isValidMovePlayer(MovePlayer move, BoardStatus status) {
		try {
			return isValidPlayerAction(move, status)
					&& isCorrectMovePlayer(move, status);
		} catch (NullPointerException e) {
			LOGGER.log(Level.FINE, messageNull, e);
			return false;
		}
	}

	/** Check if a move that can be done by a player is valid */
	private static boolean isValidPlayerAction(PlayerAction move,
			BoardStatus status) {
		return isCorrectPlayer(move, status)
				&& isAffordable(move, status)
				&& isCorrectTurn(move, status.getCurrentPlayer().getLastMoves());
	}

	/** Check if an automatic move (not done by a player) is valid */
	public static boolean isValidMoveBlackSheep(MoveBlackSheep move,
			BoardStatus status) {
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
		return boardStatus.getEquivalentPlayer(move.getPlayer()).equals(
				boardStatus.getCurrentPlayer());
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
		int money = boardStatus.getEquivalentPlayer(move.getPlayer())
				.getMoney();

		// Calculate the cost
		int cost = MoveCostCalculator.getMoveCost(move, boardStatus);

		return cost <= money;
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
		List<Terrain> adjacentTerrains = new ArrayList<Terrain>();

		for (Terrain t : boardStatus.getEquivalentPlayer(move.getPlayer())
				.getPosition().getAdjacentTerrains()) {
			adjacentTerrains.add(t);
		}

		// get where the sheep is moving
		Terrain coming = move.getMovedSheep().getPosition();
		Terrain going = move.getNewPositionOfTheSheep();

		// check if the move is actually valid
		return adjacentTerrains.contains(coming)
				&& adjacentTerrains.contains(going);
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

		// get the adjacent terrains types
		List<TerrainType> adjTerrainsTypes = new ArrayList<TerrainType>();

		for (Terrain t : boardStatus.getEquivalentPlayer(move.getPlayer())
				.getPosition().getAdjacentTerrains()) {
			adjTerrainsTypes.add(t.getTerrainType());
		}

		// now check if the type is valid
		return adjTerrainsTypes.contains(tBuying)
				&& adjTerrainsTypes.contains(tBuying);
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
		if (oldMoves.isEmpty()) {
			return true;
		}

		// we have at least one oldMove
		Move firstMove = oldMoves.get(0);

		// the second is correct if it's different from the first or if they're
		// both move player
		if (oldMoves.size() == 1) {
			return !moveToCheck.getClass().isInstance(firstMove)
					|| (MovePlayer.class.isInstance(firstMove))
					|| (MovePlayer.class.isInstance(moveToCheck));
		}

		// this is the third move, is correct if: 1)they're all different 2)it's
		// equals to the first and the second is a moveplayer 3)it's equals to
		// the second and they're both moveplayer => all different or the second
		// is a move player
		Move secondMove = oldMoves.get(1);
		return (!firstMove.getClass().isInstance(moveToCheck) && (!secondMove
				.getClass().isInstance(moveToCheck)))
				|| (MovePlayer.class.isInstance(secondMove));
	}

	//
	//
	// HERE STARTS THE METHODS RELATED TO THE ADVANCED RULES
	//
	//

	/**
	 * A MoveWolf is valid if: 1) the new terrain is adjacent to the old one.
	 * 2)the two terrains are separated by a road without a gate OR all the
	 * roads are occupied 3) the eaten sheep is null or is a sheep of the new
	 * terrain (but not the black sheep)
	 */
	public static boolean isValidMoveWolf(MoveWolf moveWolf,
			BoardStatusExtended boardStatus) {
		try {
			Terrain coming = boardStatus.getWolf().getPosition();
			Terrain going = moveWolf.getNewPosition();

			// Check condition 1
			boolean condition1 = false;
			if (coming.getAdjacentTerrains(boardStatus.getRoadMap()).contains(
					going)) {
				condition1 = true;
			}

			// check condition 2
			boolean condition2 = false;

			boolean isFree = true;
			Road used = coming.getLinkWith(going, boardStatus.getRoadMap());
			if (!boardStatus.isFreeFromGates(used)) {
				isFree = false;
			}

			if (isFree || boardStatus.isClosedByGates(coming)) {
				condition2 = true;
			}

			// check condition 3
			boolean condition3 = false;
			if (moveWolf.getKilledSheep() == null) {
				condition3 = true;
			}

			if (moveWolf.getKilledSheep() != null
					&& !BlackSheep.class.isInstance(moveWolf.getKilledSheep())
					&& moveWolf.getKilledSheep().getPosition().equals(going)) {
				condition3 = true;
			}

			return condition1 && condition2 && condition3;
		} catch (NullPointerException e) {
			LOGGER.log(Level.INFO, messageNull, e);
			return false;
		}
	}

	/**
	 * A mate is valid if: 1) the terrain is adjacent to the shepherd 2) In the
	 * terrain there is at least a maleSheep and a femaleSheep. Being a player
	 * action a mating must also be a valid player action
	 */
	public static boolean isValidMating(Mating move, BoardStatus boardStatus) {
		try {
			// get info
			Terrain terrain = move.getTerrain();
			Road playerPosition = boardStatus.getEquivalentPlayer(
					move.getPlayer()).getPosition();

			// check condition 1
			boolean okTerrain = false;
			for (Terrain t : playerPosition.getAdjacentTerrains()) {
				if (t.equals(terrain)) {
					okTerrain = true;
					break;
				}
			}

			// check condition 2
			boolean female = false;
			boolean male = false;
			for (Sheep s : boardStatus.getSheeps()) {
				if (s.getPosition().equals(terrain)) {
					if (s.getTypeOfSheep().equals(TypeOfSheep.MALESHEEP)) {
						male = true;
					} else if (s.getTypeOfSheep().equals(
							TypeOfSheep.FEMALESHEEP)) {
						female = true;
					}
				}
			}

			return male && female && okTerrain
					&& isValidPlayerAction(move, boardStatus);
		} catch (NullPointerException e) {
			LOGGER.log(Level.INFO, messageNull, e);
			return false;
		}
	}

	/**
	 * a butchering is valid if the designated sheep is in a terrain adjacent to
	 * the player's road and if the sheep is not a black sheep. Being a player
	 * action a butchering must also be a valid player action
	 */
	public static boolean isValidButchering(Butchering move,
			BoardStatusExtended boardStatus) {
		try {
			// check the black sheep
			if (BlackSheep.class.isInstance(move.getKilledSheep())) {
				return false;
			}

			Player p = boardStatus.getEquivalentPlayer(move.getPlayer());
			Sheep s = boardStatus.getEquivalentSheep(move.getKilledSheep());

			// check the terrain
			boolean ok = false;
			for (Terrain t : p.getPosition().getAdjacentTerrains()) {
				if (t.equals(s.getPosition())) {
					ok = true;
					break;
				}
			}

			return ok && isValidPlayerAction(move, boardStatus);
		} catch (NullPointerException e) {
			LOGGER.log(Level.INFO, messageNull, e);
			return false;
		}
	}

	/**
	 * A market offer is valid if it's done by the offerer (we can't offer cards
	 * of other players), if the player has that card and if the card is not an
	 * initial card
	 */
	public static boolean isValidMarketOffer(MarketOffer marketOffer,
			Player offerer) {
		if (!marketOffer.getOfferer().equals(offerer)) {
			return false;
		}

		if (!offerer.getCards().contains(marketOffer.getCardOffered())) {
			return false;
		}

		if (marketOffer.getCardOffered().isInitial()) {
			return false;
		}

		return true;
	}

	/**
	 * A market buy is valid if in the given list of market offers we have an
	 * offer for the same card and if the buy is done by the current player. The
	 * buyer must have the coins to perform the buy
	 */
	public static boolean isValidMarketBuy(MarketBuy buy,
			List<MarketOffer> offers, Player buyer) {
		if (!buy.getBuyer().equals(buyer)) {
			return false;
		}

		MarketOffer rightOffer = null;
		for (MarketOffer offer : offers) {
			if (offer.getCardOffered().equals(buy.getCardBought())) {
				rightOffer = offer;
				break;
			}
		}

		if (rightOffer == null) {
			return false;
		}

		return buyer.getMoney() >= rightOffer.getPrice();
	}
}
