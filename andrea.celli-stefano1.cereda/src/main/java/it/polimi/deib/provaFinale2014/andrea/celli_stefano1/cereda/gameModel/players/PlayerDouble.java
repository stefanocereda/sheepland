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
	 * This variable is set at the beginning of each turn and indicates if the
	 * player wants to use the first or the second shepherd
	 */
	private boolean usingSecond = false;

	/** Set if this player is using the second player in this turn */
	public void setShepherd(boolean usingSecond) {
		this.usingSecond = usingSecond;
	}

	/**
	 * Move the controlled shepherd in a new road, without rules checking.
	 * 
	 * @param newRoad
	 *            The new road
	 */
	@Override
	public void move(Road newRoad) {
		if (usingSecond) {
			secondposition = newRoad;
		} else {
			super.move(newRoad);
		}
	}

	/**
	 * Get the position of the controlled shepherd
	 */
	public Road getPosition() {
		if (usingSecond)
			return secondposition;
		return super.getPosition();
	}

	/** Get the position of the second shepherd */
	public Road getSecondposition() {
		return secondposition;
	}

	public Road getFirstPosition() {
		return super.getPosition();
	}
}
