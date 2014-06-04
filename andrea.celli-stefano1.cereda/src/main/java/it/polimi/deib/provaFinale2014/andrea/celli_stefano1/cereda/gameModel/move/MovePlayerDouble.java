/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameController.server.RuleChecker;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BoardStatus;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

/**
 * This class is used in the game with two clients. It specifies the action of
 * moving a shepherd, but also specifies which shepherd are we moving
 * 
 * @author Stefano
 * 
 */
public class MovePlayerDouble extends MovePlayer {
	private static final long serialVersionUID = -477264249246032003L;

	private int shepherd;

	/**
	 * 
	 * @param player
	 *            The player moved
	 * @param newPosition
	 *            The destination road
	 * @param shepherdMoved
	 *            The shepherd moved (1 or 2)
	 */
	public MovePlayerDouble(Player player, Road newPosition, int shepherdMoved) {
		super(player, newPosition);
		shepherd = shepherdMoved;
	}

	/** @return the moving shepherd */
	public int getShepherd() {
		return shepherd;
	}

	@Override
	public boolean isValid(BoardStatus boardStatus) {
		return RuleChecker.isValidMovePlayerDouble(this, boardStatus);
	}
}
