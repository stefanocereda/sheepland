package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Player;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.Terrain;

/**
 * MoveBlackSheep defines the action of moving the black sheep (as a "standard"
 * sheep)
 * 
 * @author Andrea Celli
 * 
 */
public class MoveBlackSheep extends Move {
	Terrain newPosition;

	/**
	 * MoveBlackSheep()
	 * 
	 * @param player
	 *            the player who moves the black sheep
	 * @param newPosition
	 *            the new position of the black sheep
	 */
	public MoveBlackSheep(Player player, Terrain newPosition) {
		super(player);
		this.newPosition = newPosition;
	}

	// this method returns the new position of the black sheep
	public Terrain getNewPositionOfTheBlackSheep() {
		return newPosition;
	}

}
