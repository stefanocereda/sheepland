package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.animals;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

/**
 * A wolf is an animal
 * 
 * @author Stefano
 * 
 */
public class Wolf extends Animal {
    private static final long serialVersionUID = -4738851603905961543L;

    /** Wolf constructor requires the initial position (terrain) */
    public Wolf(Terrain position) {
        super(position);
    }
}
