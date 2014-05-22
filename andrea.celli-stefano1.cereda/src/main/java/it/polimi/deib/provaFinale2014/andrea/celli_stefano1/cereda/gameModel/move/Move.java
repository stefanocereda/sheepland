package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.move;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.GenericGameObject;

import java.io.Serializable;

/**
 * A Move represents all the type of moves available in the game and the actors
 * involved in a move. This class will be used for client/server communications,
 * for rule checking and to actually execute them.
 * 
 * Specific types of moves are built through inheritance of the class Move
 * 
 * @author Andrea Celli
 */
public class Move extends GenericGameObject implements Serializable {

}