package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.client;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.costants.Costants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.BuyCardMove;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveBlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.PlayerAction;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Gate;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

/**
 * This class contains the methods for executing the moves comunicated by the
 * server. It updates the model.
 * 
 * @author Andrea
 * 
 */
public class ExecuteAction {

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
	public void executeMoveSheep(MoveSheep move, BoardStatus boardStatus) {
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
	public void executeMoveBlackSheep(MoveBlackSheep move,
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
	public void executeMovePlayer(MovePlayer move, BoardStatus boardStatus) {
		Player player = boardStatus.getEquivalentPlayer(move.getPlayer());
		Road oldPositionOfThePlayer = player.getPosition();
		player.move(move.getNewPositionOfThePlayer());
		player.subtractMoney(move.getCost());
		addMoveToLastMoves(move, boardStatus);
		addGate(oldPositionOfThePlayer, boardStatus);
	}

	/**
	 * This method adds a gate in a road. It checks whether the gate has to be
	 * final or not. This method doesn't check if there are final gates
	 * available, it has to be done by the server!!!
	 * 
	 * @param road
	 *            The road in which the gate has to be placed.
	 * @param boardStatus
	 *            The status of the current game
	 */
	private static void addGate(Road road, BoardStatus boardStatus) {
		if (boardStatus.countStandardGates(boardStatus.getGates()) < Costants.NUMBER_OF_NON_FINAL_GATES)
			boardStatus.addPlacedGateToBoardStatus(new Gate(false, road));
		else
			boardStatus.addPlacedGateToBoardStatus(new Gate(true, road));
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
	public void executeBuyCardMove(BuyCardMove move, BoardStatus boardStatus) {
		Card card = move.getNewCard();
		Player player = boardStatus.getEquivalentPlayer(move.getPlayer());
		boardStatus.getDeck().remove(card);
		player.addCard(card);
		player.subtractMoney(card.getNumber());
		addMoveToLastMoves(move, boardStatus);
	}
}
