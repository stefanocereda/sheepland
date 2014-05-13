package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;

/**
 * The basic animal class, used to inherit
 * 
 * @author Stefano
 * 
 */
public class Animal {

	/** The position of the animal */
	private Terrain position;

	/**
	 * Move the animal in a new position, without checking if that move is valid
	 * 
	 * @param newPosition
	 *            The new position for the animal
	 */
	public void move(Terrain newPosition) {
		position = newPosition;
	}

	/** Get the position of the animal */
	public Terrain getPosition() {
		return position;
	}
}
