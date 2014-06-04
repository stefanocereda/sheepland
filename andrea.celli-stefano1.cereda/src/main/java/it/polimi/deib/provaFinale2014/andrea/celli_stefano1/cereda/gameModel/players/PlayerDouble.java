/**
 * 
 */
package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.players;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;

/**
 * This is the player used in the games with two clients
 * 
 * @author Stefano
 * 
 */
public class PlayerDouble extends Player {
	private static final long serialVersionUID = -2210970681587172191L;

	/** The position of the second shepherd */
	private Road secondposition;

	/**
	 * @return the secondposition
	 */
	public Road getSecondposition() {
		return secondposition;
	}

	/**
	 * @param secondposition
	 *            the secondposition to set
	 */
	public void moveSecond(Road secondposition) {
		this.secondposition = secondposition;
	}
}
