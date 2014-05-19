package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.gameControllerClient;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.Move;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveBlackSheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MovePlayer;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move.MoveSheep;

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
	 */
	private static void addMoveToLastMoves(Move move) {
		move.getPlayer().addLastMove(move);
	}

	/**
	 * This method executes a MoveSheep move
	 * 
	 * @param move
	 *            MoveSheep
	 */
	public void executeMoveSheep(MoveSheep move) {
		addMoveToLastMoves(move);
		move.getMovedSheep().move(move.getNewPositionOfTheSheep());
	}

	/**
	 * This method executes a MoveBlackSheep move
	 * 
	 * @param move
	 *            (the new destination of the blackSheep)
	 */
	public void executeMoveBlackSheep(MoveBlackSheep move) {
		move.getBlackSheep().move(move.getNewPositionOfTheBlackSheep());
	}

	/**
	 * This method execute a MovePlayer move.
	 * 
	 * @param move
	 *            (The cost of the move has to be calculated by the server!!)
	 */
	public void executeMovePlayer(MovePlayer move) {
		Player player = move.getPlayer();
		player.move(move.getNewPositionOfThePlayer());
		player.subtractMoney(move.getCost());
		addMoveToLastMoves(move);
	}
}
