package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.GenericGameObject;

/**
 * A gate is placed on roads to separate territories, it can be a "last gate"
 * 
 * @author Stefano
 * 
 */
public class Gate extends GenericGameObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2151263876430505393L;
	/** The gate is last if placed during the last turn */
	private boolean last;
	/** The gate is placed on a road */
	private Road position;

	/**
	 * Constructor of a gate requires to know if the Gate is a last one and its
	 * position
	 */
	public Gate(boolean last, Road roadPosition) {
		this.last = last;
		this.position = roadPosition;
	}

	public boolean isLast() {
		return last;
	}

	public Road getPosition() {
		return position;
	}

}
