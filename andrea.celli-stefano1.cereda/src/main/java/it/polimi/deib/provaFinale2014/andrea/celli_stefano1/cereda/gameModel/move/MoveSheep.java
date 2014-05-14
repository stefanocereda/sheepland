package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Sheep;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Terrain;

/**
 * MoveSheep takes account of the sheep which has been moved, its destination
 * and its previous location.
 * 
 * @author Andrea Celli
 * 
 */

public class MoveSheep extends Move {
	private Sheep sheep;
	private Terrain newPosition;

	/**
	 * The constructor creates a new move in which a sheep is moved to another
	 * terrain
	 * 
	 * @param player
	 *            (who execute the action)
	 * @param sheep
	 *            (the sheep which is moved)
	 * @param newPosition
	 *            (where the sheep is moved)
	 */
	public MoveSheep(Player player, Sheep sheep, Terrain newPosition) {
		super(player);
		this.sheep = sheep;
		this.newPosition = newPosition;
	}

	// returns the sheep that has been moved
	public Sheep getMovedSheep() {
		return sheep;
	}

	// returns the new position of the sheep
	public Terrain getNewPositionOfTheSheep() {
		return newPosition;
	}
}
