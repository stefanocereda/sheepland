package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GameConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatusExtended;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.TypeOfSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Dice;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Gate;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

/**
 * This class contains the methods for executing the moves communicated by the
 * server. It updates the model. It's implemented statically
 * 
 * @author Andrea
 * @author Stefano (advanced moves)
 * 
 */
public class ExecuteAction {

	/** Override the default constructor */
	private ExecuteAction() {
	}

	/**
	 * This method add the move as the last move of the player who execute it
	 * 
	 * @param move
	 * @param boardStatus
	 */
	private static void addMoveToLastMoves(PlayerAction move,
			BoardStatus boardStatus) {
		boardStatus.getEquivalentPlayer(move.getPlayer()).addLastMove(move);
	}

	/**
	 * This method executes a MoveSheep move
	 * 
	 * @param move
	 *            MoveSheep
	 * @param boardStatus
	 */
	public static void executeMoveSheep(MoveSheep move, BoardStatus boardStatus) {
		addMoveToLastMoves(move, boardStatus);
		boardStatus.getEquivalentSheep(move.getMovedSheep()).move(
				move.getNewPositionOfTheSheep());
	}

	/**
	 * This method executes a MoveBlackSheep move (It's the move decided by the
	 * dice, not by a player)
	 * 
	 * @param move
	 *            (the new destination of the blackSheep)
	 * @param boardStatus
	 *            the boardStatus of the current game
	 */
	public static void executeMoveBlackSheep(MoveBlackSheep move,
			BoardStatus boardStatus) {
		boardStatus.getBlackSheep().move(move.getNewPositionOfTheBlackSheep());
	}

	/**
	 * This method execute a MovePlayer move.
	 * 
	 * @param move
	 *            (The cost of the move has to be calculated by the server!!)
	 * @param boardStatus
	 *            The boardStatus of the current game
	 */
	public static void executeMovePlayer(MovePlayer move,
			BoardStatus boardStatus) {
		Player player = boardStatus.getEquivalentPlayer(move.getPlayer());
		Road oldPositionOfThePlayer = player.getPosition();
		player.subtractMoney(MoveCostCalculator.getMoveCost(move, boardStatus));
		player.move(move.getNewPositionOfThePlayer());
		addMoveToLastMoves(move, boardStatus);
		addGate(oldPositionOfThePlayer, boardStatus);
	}

	/**
	 * This method adds a gate in a road. It checks whether the gate has to be
	 * final or not. This method doesn't check if there are final gates
	 * available, it has to be done by the server!!! Before creating a gate we
	 * check if the sent road is different from null because when the players
	 * first choose an initial road we create a move to represent this decision,
	 * but we don't want to create gates placed on null
	 * 
	 * @param road
	 *            The road in which the gate has to be placed.
	 * @param boardStatus
	 *            The status of the current game
	 */
	private static void addGate(Road road, BoardStatus boardStatus) {
		if (road != null) {
			if (boardStatus.countStandardGates() < GameConstants.NUMBER_OF_NON_FINAL_GATES) {
				boardStatus.addPlacedGateToBoardStatus(new Gate(false, road));
			} else {
				boardStatus.addPlacedGateToBoardStatus(new Gate(true, road));
			}
		}
	}

	/**
	 * This method execute a BuyCardMove move.
	 * 
	 * @param move
	 *            (The cost of the move, in this case the cost of the card, is
	 *            specified by the card!!!)
	 * @param boardStatus
	 *            The boardStatus of the current game.
	 */
	public static void executeBuyCardMove(BuyCardMove move,
			BoardStatus boardStatus) {
		Card card = move.getNewCard();
		Player player = boardStatus.getEquivalentPlayer(move.getPlayer());
		boardStatus.getDeck().remove(card);
		player.addCard(card);
		player.subtractMoney(card.getNumber());
		addMoveToLastMoves(move, boardStatus);
	}

	//
	//
	// HERE STARTS THE METHODS FOR ADVANCED RULES
	//
	//

	/** Move the wolf and destroy the sheep */
	public static void executeMoveWolf(MoveWolf moveWolf,
			BoardStatusExtended boardStatus) {
		boardStatus.getWolf().move(moveWolf.getNewPosition());
		boardStatus.getSheeps().remove(moveWolf.getKilledSheep());
	}

	/**
	 * This move has to be executed only by the server. Roll a dice, if the
	 * result equals the number on the road where is the player create a new
	 * lamb and put it on the given terrain
	 */
	public static void executeMating(Mating move,
			BoardStatusExtended boardStatus) {
		Dice dice = Dice.create();
		int rolled = dice.roll(GameConstants.NUMBER_OF_DICE_SIDES);
		Player player = boardStatus.getEquivalentPlayer(move.getPlayer());

		if (player.getPosition().getBoxValue() != rolled) {
			return;
		}

		Sheep newLamb = new Sheep(0, TypeOfSheep.NORMALSHEEP, move.getTerrain());
		newLamb.setID();
		boardStatus.addSheep(newLamb);
	}

	/**
	 * This move has to be executed only by the server. Roll a dice, if the
	 * result equals the number on the road where is the player go on. Roll a
	 * dice for every player near to the acting player, every player that scores
	 * a number greater than five receives two coins from the player, if the
	 * player has insufficient founds the move is aborted. Otherwise the sheep
	 * is killed
	 */
	public static void executeButchering(Butchering move,
			BoardStatusExtended boardStatus) {
		Player player = boardStatus.getEquivalentPlayer(move.getPlayer());
		Sheep sheep = boardStatus.getEquivalentSheep(move.getKilledSheep());

		Dice dice = Dice.create();

		// roll a dice for the acting player
		int roll = dice.roll(GameConstants.NUMBER_OF_DICE_SIDES);
		if (roll != player.getPosition().getBoxValue()) {
			return;
		}

		// search the adjacent players
		List<Player> nextPlayers = new ArrayList<Player>();
		for (Player p : boardStatus.getPlayers()) {
			if (p.getPosition().getNextRoads().contains(player.getPosition())) {
				nextPlayers.add(p);
			}
		}

		// roll a dice for every adjacent player
		for (Player p : nextPlayers) {
			roll = dice.roll(GameConstants.NUMBER_OF_DICE_SIDES);
			if (roll < GameConstants.MINIMUN_SCORE_IN_BUTCHERING) {
				nextPlayers.remove(p);
			}
		}

		// compute the total money that the player has to pay
		int total = nextPlayers.size()
				* GameConstants.COINS_GIVEN_IN_BUTCHERING;
		if (total > player.getMoney()) {
			return;
		}

		// give the money
		player.subtractMoney(total);
		for (Player p : nextPlayers) {
			p.addMoney(GameConstants.COINS_GIVEN_IN_BUTCHERING);
		}

		// kill the sheep
		boardStatus.getSheeps().remove(sheep);
	}
}
