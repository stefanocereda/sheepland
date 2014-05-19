package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.BlackSheep;
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
	private Terrain newPosition;
	private BlackSheep blackSheep;

	/**
	 * @param player
	 *            the player who moves the black sheep
	 * @param newPosition
	 *            the new position of the black sheep
	 */
	public MoveBlackSheep(Player player, Terrain newPosition,
			BlackSheep blackSheep) {
		super(player);
		this.blackSheep = blackSheep;
		this.newPosition = newPosition;
	}

	/** @return the new position of the black sheep */
	public Terrain getNewPositionOfTheBlackSheep() {
		return newPosition;
	}

	/** @return the blackSheep of the specific game */
	public BlackSheep getBlackSheep() {
		return blackSheep;
	}

}
