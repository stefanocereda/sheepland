package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.GenericGameObject;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

/**
 * The basic animal class, used to inherit
 * 
 * @author Stefano
 * 
 */
public class Animal extends GenericGameObject {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7465645374799885568L;
    /** The position of the animal */
    private Terrain position;

    /** The animal constructor sets the position */
    public Animal(Terrain pos) {
        position = pos;
    }

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
