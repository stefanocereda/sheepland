package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

/**
 * MovePlayer defines the action of moving a shepherd to a new position
 * 
 * @author Andrea Celli
 * 
 */
public class MovePlayer extends PlayerAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4825964119025966375L;
	private Road newPosition;

	/**
	 * The constructor creates a new move in which a shepherd is moved to a new
	 * road
	 * 
	 * @param player
	 *            the moved player (which is also the moving player)
	 * @param newPosition
	 *            the new road in which the player is placed
	 */
	public MovePlayer(Player player, Road newPosition) {
		super(player);
		this.newPosition = newPosition;
	}

	/** @return the new position of the player */
	public Road getNewPositionOfThePlayer() {
		return newPosition;
	}

	@Override
	public boolean isValid(BoardStatus boardStatus) {
		return RuleChecker.isValidMovePlayer(this, boardStatus);
	}

	@Override
	public void execute(BoardStatus boardStatus) {
		ExecuteAction.executeMovePlayer(this, boardStatus);
	}
}
