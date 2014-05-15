package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Road;

/**
 * MovePlayer defines the action of moving a shepherd to a new position
 * 
 * @author Andrea Celli
 * 
 */
public class MovePlayer extends Move {
	Road newPosition;

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

}