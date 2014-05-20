package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players.Player;

/**
 * MoveSheep takes account of the sheep which has been moved, its destination
 * and its previous location.
 * 
 * @author Andrea Celli
 * 
 */

public class MoveSheep extends PlayerAction {
	private Sheep sheep;
	private Terrain newPosition;

	/**
	 * The constructor creates a new move in which a sheep is moved to another
	 * terrain
	 * 
	 * @param player
	 *            who execute the action
	 * @param sheep
	 *            the sheep which is moved
	 * @param newPosition
	 *            where the sheep is moved
	 */
	public MoveSheep(Player player, Sheep sheep, Terrain newPosition) {
		super(player);
		this.sheep = sheep;
		this.newPosition = newPosition;
	}

	/** @return the sheep that has been moved */
	public Sheep getMovedSheep() {
		return sheep;
	}

	/** @return the new position of the sheep */
	public Terrain getNewPositionOfTheSheep() {
		return newPosition;
	}
}
